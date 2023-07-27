package com.legacycode.eureka.dex.test

import java.io.File

class TestArtifact(private val filename: String) {
  val file: File
    get() = File("./src/test/resources/jars/$filename")
}
