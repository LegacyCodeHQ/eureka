package com.legacycode.tumbleweed

import java.io.File
import kotlin.reflect.KClass

sealed class SampleClass(
  private val kClass: KClass<*>,
  private val sourceLanguage: String,
) {
  val file: File
    get() {
      val compiledClassesDirectory = "../samples/build/classes/$sourceLanguage/main"
      return File(compiledClassesDirectory, kClass.java.name.replace('.', File.separatorChar) + ".class")
    }

  class Java(kClass: KClass<*>) : SampleClass(kClass, "java")

  class Kotlin(kClass: KClass<*>) : SampleClass(kClass, "kotlin")
}
