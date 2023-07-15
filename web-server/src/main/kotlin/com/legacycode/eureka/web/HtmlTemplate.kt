package com.legacycode.eureka.web

import com.legacycode.eureka.hierarchy.HierarchyServer
import java.io.InputStream

class HtmlTemplate(private val text: String) {
  private val placeholderValueMap = mutableMapOf<Placeholder, String>()

  val content: String
    get() {
      var result: String = text
      for ((placeholder, value) in placeholderValueMap) {
        result = result.replace(placeholder.handlebar, value)
      }
      return result
    }

  companion object {
    fun fromResource(path: String): HtmlTemplate {
      return fromInputStream(HierarchyServer::class.java.getResourceAsStream(path)!!)
    }

    private fun fromInputStream(inputStream: InputStream): HtmlTemplate {
      val content = inputStream
        .bufferedReader()
        .use { it.readText() }

      return HtmlTemplate(content)
    }
  }

  fun bind(placeholder: Placeholder, value: String): HtmlTemplate {
    placeholderValueMap[placeholder] = value
    return this
  }
}

@JvmInline
value class Placeholder(private val key: String) {
  val handlebars: String get() = "{{${key}}}"
}
