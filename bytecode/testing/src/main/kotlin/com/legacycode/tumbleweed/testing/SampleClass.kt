package com.legacycode.tumbleweed.testing

import java.io.File
import kotlin.reflect.KClass

sealed class SampleClass(
  private val kClass: KClass<*>,
  private val sourceLanguage: String,
) {
  companion object {
    private const val BYTECODE_MODULE_DIRECTORY = "bytecode"
    private val WORKING_DIRECTORY = File(".")
  }

  class Java(kClass: KClass<*>) : SampleClass(kClass, "java")

  class Kotlin(kClass: KClass<*>) : SampleClass(kClass, "kotlin")

  val file: File
    get() {
      val bytecodeDirectoryPath = findBytecodeDirectoryPath().absolutePath
      val compiledClassesDirectory = "$bytecodeDirectoryPath/samples/build/classes/$sourceLanguage/main"
      return File(compiledClassesDirectory, kClass.filePath)
    }

  private val KClass<*>.filePath: String
    get() = java.name.replace('.', File.separatorChar) + ".class"

  private fun findBytecodeDirectoryPath(): File {
    var bytecodeDirectory = WORKING_DIRECTORY.absoluteFile
    while (!bytecodeDirectory.resolve(BYTECODE_MODULE_DIRECTORY).exists()) {
      bytecodeDirectory = bytecodeDirectory.parentFile
    }
    return bytecodeDirectory.resolve(BYTECODE_MODULE_DIRECTORY)
  }
}
