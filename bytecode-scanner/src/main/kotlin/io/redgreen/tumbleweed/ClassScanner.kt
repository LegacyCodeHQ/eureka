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

    val simplify = classStructures.combine().first().simplify()
    return simplify
  }

  private fun classStructure(
    classFile: File,
    outClassFilesQueue: ArrayDeque<File>,
    visitedClassFiles: Set<File>,
  ): ClassStructure {
    val outFields = mutableListOf<Field>()
    val outMethods = mutableListOf<Method>()
    val outRelationships = mutableListOf<Relationship>()

    val classInfo = ClassInfo.from(classFile)
    val topLevelType = classInfo.topLevelType

    val classVisitor = object : ClassVisitor(ASM_API_VERSION) {
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

    val allMembersInRelationships = outRelationships.flatMap { listOf(it.source, it.target) }
    val methodsFromRelationships = allMembersInRelationships
      .filterIsInstance<Method>()
      .filter { it.owner == topLevelType }
      .distinct()
    val fieldsFromRelationships = allMembersInRelationships.filterIsInstance<Field>().distinct()

    val missingFields = fieldsFromRelationships - outFields.toSet()
    val missingMethods = methodsFromRelationships - outMethods.toSet()

    return ClassStructure(
      classInfo.packageName,
      classInfo.className,
      outFields + missingFields,
      outMethods + missingMethods,
      outRelationships.toList(),
    )
  }

  private fun List<ClassStructure>.combine(): List<ClassStructure> {
    val combinedClassStructures = mutableListOf<ClassStructure>()

    for (classStructure in this) {
      val syntheticInnerClassRelationships = classStructure.relationships
        .filter { it.type == Relationship.Type.Calls }
        .filter { (it.target as Method).name == "<init>" }
        .filter { it.target.owner != classStructure.className }

      val referencesRelationships = mutableListOf<Relationship>()
      for (relationship in syntheticInnerClassRelationships) {
        val innerClassConstructor = relationship.target as Method
        val innerClass = this.find { innerClassConstructor.owner.endsWith(it.className) }!!

        val outerClassAccessorsRelationships = innerClass.relationships
          .filter { it.target.owner.endsWith(classStructure.className) }

        for (accessorRelationship in outerClassAccessorsRelationships) {
          referencesRelationships.add(
            Relationship(relationship.source, accessorRelationship.target, Relationship.Type.References)
          )
        }
      }
      val updatedClassStructure = classStructure
        .copy(relationships = classStructure.relationships + referencesRelationships)
      combinedClassStructures.add(updatedClassStructure)
    }

    return combinedClassStructures.toList()
  }
}
