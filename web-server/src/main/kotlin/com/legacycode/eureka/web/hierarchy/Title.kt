package com.legacycode.eureka.web.hierarchy

class Title(private val filename: String, private val className: String) {
  val displayText: String
    get() = "$filename (${className.substring(className.lastIndexOf('.') + 1)})"
}
