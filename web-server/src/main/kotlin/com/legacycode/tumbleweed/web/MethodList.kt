package com.legacycode.tumbleweed.web

class MethodList(private val content: String) {
  companion object {
    fun fromResource(className: String): MethodList {
      val methodListResourceFilePath = "/method-lists/android-33/$className.methods"
      val content = MethodList::class.java
        .getResourceAsStream(methodListResourceFilePath)!!
        .bufferedReader()
        .readText()
      return MethodList(content)
    }
  }

  val methodSignatures: List<String> by lazy {
    content.trim().lines()
  }

  fun has(signature: String): Boolean =
    methodSignatures.contains(signature)
}
