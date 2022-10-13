package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import java.io.File
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes.ASM7

object ClassScanner {
  fun scan(classFile: File): ClassStructure {
    var className: String? = null
    var packageName: String? = null
    val outFields = mutableListOf<Field>()
    val outMethods = mutableListOf<Method>()
    val outRelationships = mutableListOf<Relationship>()
    var topLevelType: String? = null

    val classVisitor = object : ClassVisitor(ASM7) {
      override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?,
      ) {
        topLevelType = name
        name.split("/").let { fqClassNameParts ->
          packageName = fqClassNameParts.dropLast(1).joinToString(".")
          className = fqClassNameParts.last()
        }
      }

      override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
      ): FieldVisitor {
        outFields.add(Field(name!!, FieldDescriptor.from(descriptor!!)))
        return object : FieldVisitor(ASM7) { /* no-op */ }
      }

      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
      ): MethodVisitor {
        val method = Method(name!!, MethodDescriptor(descriptor!!))
        outMethods.add(method)
        return InstructionScanner.scan(topLevelType!!, method, outRelationships)
      }
    }

    ClassReader(BufferedInputStream(classFile.inputStream())).accept(classVisitor, 0)

    return ClassStructure(
      packageName!!,
      className!!,
      outFields.toList(),
      outMethods.toList(),
      outRelationships.toList(),
    ).simplify()
  }
}
