package com.legacycode.tumbleweed.testing

import java.io.File

class TestClassResource(private val className: String) {
  val file: File
    get() = File("./src/test/resources/classes/$className.class")
}
