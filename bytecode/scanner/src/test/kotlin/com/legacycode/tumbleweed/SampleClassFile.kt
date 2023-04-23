package com.legacycode.tumbleweed

import java.io.File
import kotlin.reflect.KClass

sealed class SampleClassFile(
  private val kClass: KClass<*>,
  private val sourceLanguage: String,
) {
  val file: File
    get() {
      val compiledClassesDirectory = "../samples/build/classes/$sourceLanguage/main"
      return File(compiledClassesDirectory, kClass.java.name.replace('.', File.separatorChar) + ".class")
    }

  class Java(kClass: KClass<*>) : SampleClassFile(kClass, "java")

  class Kotlin(kClass: KClass<*>) : SampleClassFile(kClass, "kotlin")
}
