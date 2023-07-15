package com.legacycode.eureka.web

import java.io.InputStream

class HtmlTemplate(private val text: String) {
  private val placeholderValueMap = mutableMapOf<Placeholder, String>()

  val content: String
    get() {
      var result: String = text
      for ((placeholder, value) in placeholderValueMap) {
        result = result.replace(placeholder.handlebars, value)
      }
      return result
    }

  companion object {
    fun fromResource(path: String): HtmlTemplate {
      return fromInputStream(HtmlTemplate::class.java.getResourceAsStream(path)!!)
    }

    private fun fromInputStream(inputStream: InputStream): HtmlTemplate {
      val text = inputStream
        .bufferedReader()
        .use { it.readText() }

      return HtmlTemplate(text)
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
