package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes.ASM7

object ClassScanner {
  fun scan(location: ClassFileLocation): ClassStructure {
    var className: String? = null
    var packageName: String? = null
    val fields = mutableListOf<Field>()
    val methods = mutableListOf<Method>()

    val classVisitor = object : ClassVisitor(ASM7) {
      override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?,
      ) {
        name.split("/").let { fqcnParts ->
          packageName = fqcnParts.dropLast(1).joinToString(".")
          className = fqcnParts.last()
        }
      }

      override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
      ): FieldVisitor {
        return FieldScanner.scan(name, descriptor, fields)
      }

      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
      ): MethodVisitor {
        return MethodScanner.scan(name, descriptor, signature, methods)
      }
    }

    ClassReader(BufferedInputStream(location.file.inputStream())).accept(classVisitor, 0)

    return ClassStructure(packageName!!, className!!, fields.toList(), methods.toList())
  }
}
