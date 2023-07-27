package com.legacycode.eureka.testing

import java.io.FileNotFoundException

class TextResource(private val resourceFilePath: String) {
  val content: String
    get() {
      val resourceUrl = TextResource::class.java.classLoader
        .getResource(resourceFilePath)
      return resourceUrl?.readText()
        ?: throw FileNotFoundException("Unable to find file: '$resourceFilePath'")
    }
}
