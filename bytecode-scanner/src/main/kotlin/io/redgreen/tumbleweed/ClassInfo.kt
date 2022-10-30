package io.redgreen.tumbleweed

import java.io.BufferedInputStream
import java.io.File
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class ClassInfo(
  val packageName: String,
  val className: String,
  val type: QualifiedType,
) {
  companion object {
    private val logger: Logger = LoggerFactory.getLogger(ClassInfo::class.java)

    fun from(classFile: File): ClassInfo {
      var className: String? = null
      var packageName: String? = null
      var topLevelType: QualifiedType? = null

      val classVisitor = object : ClassVisitor(ASM_API_VERSION) {
        override fun visit(
          version: Int,
          access: Int,
          name: String,
          signature: String?,
          superName: String?,
          interfaces: Array<out String>?,
        ) {
          logger.debug("Visiting class: {}", name)

          topLevelType = QualifiedType(name)
          name.split("/").let { fqClassNameParts ->
            packageName = fqClassNameParts.dropLast(1).joinToString(".")
            className = fqClassNameParts.last()
          }
        }
      }

      ClassReader(BufferedInputStream(classFile.inputStream())).accept(classVisitor, 0)

      return ClassInfo(packageName!!, className!!, topLevelType!!)
    }
  }
}
