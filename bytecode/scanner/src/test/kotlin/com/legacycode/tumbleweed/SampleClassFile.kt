package com.legacycode.tumbleweed

import java.io.File
import kotlin.reflect.KClass

data class SampleClassFile(
  val compiledClassesDirectory: String,
  val fqClassName: String,
) {
  constructor(kClass: KClass<*>) : this("../samples/build/classes/java/main", kClass.java.name)

  val file: File
    get() = File(
      compiledClassesDirectory,
      fqClassName.replace('.', File.separatorChar) + ".class"
    )
}
