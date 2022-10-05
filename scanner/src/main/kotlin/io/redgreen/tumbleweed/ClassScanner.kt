package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.Opcodes.ASM7

object ClassScanner {
  fun scan(location: ClassFileLocation): ClassStructure {
    var className: String? = null
    var packageName: String? = null
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
    }

    ClassReader(BufferedInputStream(location.file.inputStream())).accept(classVisitor, 0)

    return ClassStructure(packageName!!, className!!)
  }
}
