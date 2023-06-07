package com.legacycode.eureka

import java.io.BufferedInputStream
import java.io.File
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.Handle
import net.bytebuddy.jar.asm.Label
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes.ACC_STATIC
import net.bytebuddy.jar.asm.Opcodes.ASM9
import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias ConstantPool = MutableMap<Any?, Field>

const val ASM_API_VERSION = ASM9

fun constantPool(): ConstantPool = mutableMapOf()

object ClassScanner {
  private val logger: Logger = LoggerFactory.getLogger(ClassScanner::class.java)

  fun scan(classFile: File): ClassStructure {
    val outClassFilesQueue = ArrayDeque(listOf(classFile))
    val visitedClassFiles = mutableSetOf<File>()
    val classStructures = mutableListOf<ClassStructure>()

    while (outClassFilesQueue.isNotEmpty()) {
      val classFileToScan = outClassFilesQueue.removeFirst()
      if (classFileToScan !in visitedClassFiles) {
        visitedClassFiles.add(classFileToScan)

        logger.debug("Scanning class file: {}", classFileToScan.absolutePath)
        val classStructure = classStructure(classFileToScan, visitedClassFiles, outClassFilesQueue)
        classStructures.add(classStructure)
      }
    }

    logger.debug("Condensing class structures.")
    val classStructure = classStructures.combine()

    logger.debug("Simplifying class structure.")
    return classStructure.normalize()
  }

  private fun classStructure(
    classFile: File,
    visitedClassFiles: Set<File>,
    outClassFilesQueue: ArrayDeque<File>,
  ): ClassStructure {
    lateinit var superClassName: String
    val outInterfaces = mutableListOf<QualifiedType>()
    val outFields = mutableListOf<Field>()
    val outMethods = mutableListOf<Method>()
    val outRelationships = mutableListOf<Relationship>()

    val classInfo = ClassInfo.from(classFile)
    val topLevelType = classInfo.type

    val constantPool = constantPool()

    val classVisitor = object : ClassVisitor(ASM_API_VERSION) {
      override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String,
        interfaces: Array<out String>?,
      ) {
        super.visit(version, access, name, signature, superName, interfaces)
        superClassName = superName.replace('/', '.')
        interfaces?.forEach { interfaceName ->
          outInterfaces.add(QualifiedType(interfaceName.replace('/', '.')))
        }
      }

      override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
      ): FieldVisitor {
        logger.debug("Visiting field: {}", name)

        val field = Field(name!!, FieldDescriptor.from(descriptor!!), topLevelType)
        if (access and ACC_STATIC != 0) {
          constantPool[value] = field
          logger.debug("Adding to constant pool: {} = {}", name, value)
        }

        outFields.add(field)
        return object : FieldVisitor(ASM_API_VERSION) { /* no-op */ }
      }

      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
      ): MethodVisitor {
        logger.debug("Visiting method: {}", name)

        val method = Method(name!!, MethodDescriptor(descriptor!!), topLevelType)
        outMethods.add(method)

        return object : MethodVisitor(ASM_API_VERSION) {
          private var maybeConstantFieldReferencedByInsn: Field? = null

          override fun visitFieldInsn(
            opcode: Int,
            owner: String,
            fieldName: String,
            fieldDescriptor: String,
          ) {
            logger.debug("Visiting field instruction ({}): {}/{}", opcode.instruction, owner, fieldName)

            val field = Field(fieldName, FieldDescriptor.from(fieldDescriptor), QualifiedType(owner))
            val relationship = Relationship(method, field, Relationship.Type.from(opcode))
            if (owner == topLevelType.name) {
              logger.debug("Adding relationship: {}", relationship)
              outRelationships.add(relationship)
            } else {
              logger.debug("Skipping relationship: {}", relationship)
            }
          }

          override fun visitMethodInsn(
            opcode: Int,
            owner: String,
            methodName: String,
            methodDescriptor: String,
            isInterface: Boolean,
          ) {
            logger.debug("Visiting method instruction ({}): {}/{}", opcode.instruction, owner, methodName)

            if (topLevelType.name.startsWith(owner) || owner.startsWith("${topLevelType.name}\$")) {
              val callee = Method(methodName, MethodDescriptor(methodDescriptor), QualifiedType(owner))
              val relationship = Relationship(method, callee, Relationship.Type.from(opcode))

              if (method.name == "<clinit>" && opcode == Opcodes.invokespecial) {
                logger.debug("Skipping synthetic bridge call from static block: {}", relationship)
              } else {
                logger.debug("Adding relationship: {}", relationship)
                outRelationships.add(relationship)
              }
            } else {
              logger.debug("Skipping method instruction ({}): {}/{}", opcode.instruction, owner, methodName)
            }

            if (maybeConstantFieldReferencedByInsn != null) {
              val relationship = Relationship(method, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)

              logger.debug("Adding relationship: {}", relationship)
              outRelationships.add(relationship)
              maybeConstantFieldReferencedByInsn = null
            }
          }

          override fun visitInvokeDynamicInsn(
            name: String?,
            descriptor: String?,
            bootstrapMethodHandle: Handle?,
            vararg bootstrapMethodArguments: Any?,
          ) {
            logger.debug(
              "Visiting invoke dynamic instruction: {}, {}",
              name,
              bootstrapMethodArguments.contentToString()
            )

            if (bootstrapMethodArguments.size > 1) {
              val methodHandle = bootstrapMethodArguments[1] as Handle
              val callee = Method(methodHandle.name, MethodDescriptor(methodHandle.desc), topLevelType)
              outRelationships.add(Relationship(method, callee, Relationship.Type.Calls))
            } else {
              logger.debug("Ignoring dynamic instruction: {}{}", name, descriptor)
            }
            super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, *bootstrapMethodArguments)
          }

          override fun visitLdcInsn(value: Any?) {
            logger.debug("Visiting ldc instruction: {}", value)

            maybeConstantFieldReferencedByInsn = constantPool[value]
            super.visitLdcInsn(value)
          }

          override fun visitIntInsn(opcode: Int, operand: Int) {
            logger.debug("Visiting int instruction: {}", opcode.instruction)

            maybeConstantFieldReferencedByInsn = constantPool[operand]
            super.visitIntInsn(opcode, operand)
          }

          override fun visitInsn(opcode: Int) {
            logger.debug("Visiting instruction: {}", opcode.instruction)

            if (opcode == Opcodes.iconst_m1) {
              maybeConstantFieldReferencedByInsn = constantPool[-1]
            } else if (Opcodes.isIntInsn(opcode)) {
              maybeConstantFieldReferencedByInsn = constantPool[opcode - Opcodes.iconst_0]
            }

            if (opcode == Opcodes.areturn && maybeConstantFieldReferencedByInsn != null) {
              val relationship = Relationship(method, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)
              logger.debug("Adding relationship: {}", relationship)
              outRelationships.add(relationship)
              maybeConstantFieldReferencedByInsn = null
            }
            super.visitInsn(opcode)
          }

          override fun visitJumpInsn(opcode: Int, label: Label?) {
            logger.debug("Visiting jump instruction: {}", opcode.instruction)

            if (maybeConstantFieldReferencedByInsn != null) {
              val relationship = Relationship(method, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)

              logger.debug("Adding relationship: {}", relationship)
              outRelationships.add(relationship)
              maybeConstantFieldReferencedByInsn = null
            }
            super.visitJumpInsn(opcode, label)
          }
        }
      }

      override fun visitInnerClass(name: String, outerName: String?, innerName: String?, access: Int) {
        logger.debug("Visiting inner class: {}", name)
        val innerClassFilename = "${name.substring(name.lastIndexOf('/') + 1)}.class"

        val innerClassFile = classFile.parentFile.resolve(innerClassFilename)
        if (innerClassFile.exists() && innerClassFile !in visitedClassFiles) {
          logger.debug("Adding new file to scan: ${innerClassFile.canonicalPath}")
          outClassFilesQueue.addLast(innerClassFile)
        }
      }
    }

    ClassReader(BufferedInputStream(classFile.inputStream())).accept(classVisitor, 0)

    return ClassStructure(
      classInfo.packageName,
      classInfo.className,
      QualifiedType(superClassName),
      outInterfaces.toList(),
      outFields,
      outMethods,
      outRelationships.toList(),
      topLevelType,
    )
  }
}

@Suppress("MagicNumber")
internal val Int.instruction: String
  get() {
    return when (this) {
      Opcodes.`return` -> "return"
      Opcodes.getstatic -> "getstatic"
      Opcodes.putstatic -> "putstatic"
      Opcodes.putfield -> "putfield"
      Opcodes.getfield -> "getfield"
      Opcodes.invokespecial -> "invokespecial"
      Opcodes.invokevirtual -> "invokevirtual"
      Opcodes.invokestatic -> "invokestatic"
      Opcodes.invokeinterface -> "invokeinterface"
      Opcodes.bipush -> "bipush"
      Opcodes.iconst_m1 -> "iconst_m1"
      Opcodes.iconst_0 -> "iconst_0"
      Opcodes.iconst_1 -> "iconst_1"
      Opcodes.iconst_2 -> "iconst_2"
      Opcodes.iconst_3 -> "iconst_3"
      Opcodes.iconst_4 -> "iconst_4"
      Opcodes.iconst_5 -> "iconst_5"
      Opcodes.if_icmpne -> "if_icmpne"
      Opcodes.areturn -> "areturn"
      else -> "unmapped (${"0x%02x".format(this)})})"
    }
  }
