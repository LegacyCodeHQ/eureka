package com.legacycode.eureka.dex.test

import java.io.File

class TestApk(private val filename: String) {
  val file: File
    get() = File("./src/test/resources/apks/$filename")
}
