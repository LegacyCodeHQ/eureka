package com.legacycode.eureka.android

import com.legacycode.eureka.testing.TextResource

class MethodList(private val content: String) {
  companion object {
    fun fromResource(className: String): MethodList {
      val methodListResourceFilePath = "/method-lists/android-33/$className.methods"
      val methodListContent = TextResource(methodListResourceFilePath).content
      return MethodList(methodListContent)
    }
  }

  val methodSignatures: List<String> by lazy {
    content.trim().lines()
  }

  fun has(signature: String): Boolean =
    methodSignatures.contains(signature)
}
