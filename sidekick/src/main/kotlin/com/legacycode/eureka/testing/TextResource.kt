package com.legacycode.eureka.testing

import java.io.FileNotFoundException

class TextResource(private val resourceFilePath: String) {
  val content: String
    get() {
      val pathBeginsWithSlash = resourceFilePath.startsWith('/')
      val pathToUse = if (pathBeginsWithSlash) {
        resourceFilePath
      } else {
        "/$resourceFilePath"
      }
      val resourceUrl = object {}::class.java
        .getResource(pathToUse)
      return resourceUrl?.readText()
        ?: throw FileNotFoundException("Unable to find file: '$resourceFilePath'")
    }
}
