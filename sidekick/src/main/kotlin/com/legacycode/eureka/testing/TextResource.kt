package com.legacycode.eureka.testing

import java.io.FileNotFoundException

class TextResource(private val resourceFilePath: String) {
  val content: String
    get() {
      val resourceUrl = object {}::class.java
        .getResource(resourceFilePath)
      return resourceUrl?.readText()
        ?: throw FileNotFoundException("Unable to find file: '$resourceFilePath'")
    }
}
