package io.redgreen.tumbleweed

import java.io.File

data class ClassFileLocation(
  val compiledClassesDirectory: String,
  val fqClassName: String,
) {
  val file: File
    get() = File(
      compiledClassesDirectory,
      fqClassName.replace('.', File.separatorChar) + ".class"
    )
}
