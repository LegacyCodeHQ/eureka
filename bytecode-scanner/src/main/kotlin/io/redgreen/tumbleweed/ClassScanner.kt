package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import java.io.File
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes.ASM9
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val ASM_API_VERSION = ASM9

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
        val classStructure = classStructure(classFileToScan, outClassFilesQueue, visitedClassFiles)
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
    outClassFilesQueue: ArrayDeque<File>,
    visitedClassFiles: Set<File>,
  ): ClassStructure {
    lateinit var superClassName: String
    val outInterfaces = mutableListOf<QualifiedType>()
    val outFields = mutableListOf<Field>()
    val outMethods = mutableListOf<Method>()
    val outRelationships = mutableListOf<Relationship>()

    val classInfo = ClassInfo.from(classFile)
    val topLevelType = classInfo.type

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

        outFields.add(Field(name!!, FieldDescriptor.from(descriptor!!), topLevelType))
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
        return InstructionScanner.scan(topLevelType, method, outRelationships)
      }

      override fun visitInnerClass(name: String, outerName: String?, innerName: String?, access: Int) {
        logger.debug("Visiting inner class: {}", name)

        val innerClassFile = classFile.parentFile.resolve("${name.substring(name.lastIndexOf('/') + 1)}.class")
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
