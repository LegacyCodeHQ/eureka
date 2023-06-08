package com.legacycode.eureka

import java.io.BufferedInputStream
import java.io.File
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.Handle
import net.bytebuddy.jar.asm.Label
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes
import net.bytebuddy.jar.asm.Opcodes.ACC_STATIC
import net.bytebuddy.jar.asm.Opcodes.ASM9
import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias Opcode = Int
typealias ConstantPool = MutableMap<Any?, Field>

const val ASM_API_VERSION = ASM9

fun constantPool(): ConstantPool = mutableMapOf()

class ClassScanner {
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
        val classStructure = getClassStructure(classFileToScan, visitedClassFiles, outClassFilesQueue)
        classStructures.add(classStructure)
      }
    }

    logger.debug("Condensing class structures.")
    val classStructure = classStructures.combine()

    logger.debug("Simplifying class structure.")
    return classStructure.normalize()
  }

  private fun getClassStructure(
    classFile: File,
    visitedClassFiles: Set<File>,
    outClassFilesQueue: ArrayDeque<File>,
  ): ClassStructure {
    lateinit var superClass: QualifiedType
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
        superClass = QualifiedType.from(superName)
        interfaces?.forEach { interfaceName ->
          val qualifiedType = QualifiedType.from(interfaceName)
          outInterfaces.add(qualifiedType)
        }
      }

      override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
      ): FieldVisitor? {
        logger.debug("Visiting field: {}", name)

        val field = Field(name!!, FieldDescriptor.from(descriptor!!), topLevelType)
        if (access and ACC_STATIC != 0) {
          constantPool[value] = field
          logger.debug("Adding to constant pool: {} = {}", name, value)
        }

        outFields.add(field)
        return null
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
            opcode: Opcode,
            owner: String,
            fieldName: String,
            fieldDescriptor: String,
          ) {
            logger.debug("Visiting field instruction ({}): {}/{}", opcode.insn, owner, fieldName)

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
            opcode: Opcode,
            owner: String,
            methodName: String,
            methodDescriptor: String,
            isInterface: Boolean,
          ) {
            logger.debug("Visiting method instruction ({}): {}/{}", opcode.insn, owner, methodName)

            if (topLevelType.name.startsWith(owner) || owner.startsWith("${topLevelType.name}\$")) {
              val callee = Method(methodName, MethodDescriptor(methodDescriptor), QualifiedType(owner))
              val relationship = Relationship(method, callee, Relationship.Type.from(opcode))

              if (method.name == "<clinit>" && opcode == Opcodes.INVOKESPECIAL) {
                logger.debug("Skipping synthetic bridge call from static block: {}", relationship)
              } else {
                logger.debug("Adding relationship: {}", relationship)
                outRelationships.add(relationship)
              }
            } else {
              logger.debug("Skipping method instruction ({}): {}/{}", opcode.insn, owner, methodName)
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

          override fun visitIntInsn(opcode: Opcode, operand: Int) {
            logger.debug("Visiting int instruction: {}", opcode.insn)

            maybeConstantFieldReferencedByInsn = constantPool[operand]
            super.visitIntInsn(opcode, operand)
          }

          override fun visitInsn(opcode: Opcode) {
            logger.debug("Visiting instruction: {}", opcode.insn)

            if (opcode == Opcodes.ICONST_M1) {
              maybeConstantFieldReferencedByInsn = constantPool[-1]
            } else if (opcode.isIntInsn) {
              maybeConstantFieldReferencedByInsn = constantPool[opcode - Opcodes.ICONST_0]
            }

            if (opcode == Opcodes.ARETURN && maybeConstantFieldReferencedByInsn != null) {
              val relationship = Relationship(method, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)
              logger.debug("Adding relationship: {}", relationship)
              outRelationships.add(relationship)
              maybeConstantFieldReferencedByInsn = null
            }
            super.visitInsn(opcode)
          }

          override fun visitJumpInsn(opcode: Opcode, label: Label?) {
            logger.debug("Visiting jump instruction: {}", opcode.insn)

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
        val innerClassFile = getInnerClassFile(name)

        if (innerClassFile.exists() && innerClassFile !in visitedClassFiles) {
          logger.debug("Adding new file to scan: ${innerClassFile.canonicalPath}")
          outClassFilesQueue.addLast(innerClassFile)
        }
      }

      private fun getInnerClassFile(name: String): File {
        val innerClassFilename = "${name.substring(name.lastIndexOf('/') + 1)}.class"
        return classFile.parentFile.resolve(innerClassFilename)
      }
    }

    ClassReader(BufferedInputStream(classFile.inputStream())).accept(classVisitor, 0)

    return ClassStructure(
      classInfo.packageName,
      classInfo.className,
      superClass,
      outInterfaces.toList(),
      outFields,
      outMethods,
      outRelationships.toList(),
      topLevelType,
    )
  }
}

val Opcode.isIntInsn: Boolean
  get() {
    return this == Opcodes.ICONST_M1 ||
      this == Opcodes.ICONST_0 ||
      this == Opcodes.ICONST_1 ||
      this == Opcodes.ICONST_2 ||
      this == Opcodes.ICONST_3 ||
      this == Opcodes.ICONST_4 ||
      this == Opcodes.ICONST_5
  }

@Suppress("MagicNumber")
internal val Opcode.insn: String
  get() {
    return when (this) {
      Opcodes.RETURN -> "return"
      Opcodes.GETSTATIC -> "getstatic"
      Opcodes.PUTSTATIC -> "putstatic"
      Opcodes.PUTFIELD -> "putfield"
      Opcodes.GETFIELD -> "getfield"
      Opcodes.INVOKESPECIAL -> "invokespecial"
      Opcodes.INVOKEVIRTUAL -> "invokevirtual"
      Opcodes.INVOKESTATIC -> "invokestatic"
      Opcodes.INVOKEINTERFACE -> "invokeinterface"
      Opcodes.BIPUSH -> "bipush"
      Opcodes.ICONST_M1 -> "iconst_m1"
      Opcodes.ICONST_0 -> "iconst_0"
      Opcodes.ICONST_1 -> "iconst_1"
      Opcodes.ICONST_2 -> "iconst_2"
      Opcodes.ICONST_3 -> "iconst_3"
      Opcodes.ICONST_4 -> "iconst_4"
      Opcodes.ICONST_5 -> "iconst_5"
      Opcodes.IF_ICMPNE -> "if_icmpne"
      Opcodes.ARETURN -> "areturn"
      else -> "unmapped (${"0x%02x".format(this)})"
    }
  }
