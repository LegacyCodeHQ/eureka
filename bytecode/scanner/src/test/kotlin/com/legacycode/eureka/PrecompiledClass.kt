package com.legacycode.eureka

import java.io.File

data class PrecompiledClass(
  val language: String,
  val sampleDirectoryName: String,
  val simpleClassName: String,
) {
  val file: File
    get() {
      val path = "../samples/src/main/resources/precompiled/$language/$sampleDirectoryName/$simpleClassName.class"
      return File(path)
    }
}
