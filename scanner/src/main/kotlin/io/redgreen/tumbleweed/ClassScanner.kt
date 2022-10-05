package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.Opcodes.ASM7

object ClassScanner {
  fun scan(location: ClassFileLocation): ClassStructure {
    var className: String? = null
    var packageName: String? = null
    val fields = mutableListOf<Field>()

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
    }

    ClassReader(BufferedInputStream(location.file.inputStream())).accept(classVisitor, 0)

    return ClassStructure(packageName!!, className!!, fields.toList())
  }

  class FieldScanner : FieldVisitor(ASM7) {
    companion object {
      fun scan(
        name: String?,
        descriptor: String?,
        outFields: MutableList<Field>,
      ): FieldVisitor {
        outFields.add(Field(name!!, Type(descriptor!!)))
        return object : FieldVisitor(ASM7) { /* no-op */ }
      }
    }
  }
}

data class Field(
  val name: String,
  val type: Type,
)

@JvmInline
value class Type(private val descriptor: String) {
  val name: String
    get() = descriptor
      .removePrefix("L")
      .removeSuffix(";")
      .replace("/", ".")
}
