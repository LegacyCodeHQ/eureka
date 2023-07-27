package com.legacycode.eureka.web

import com.legacycode.eureka.testing.TextResource

class HtmlTemplate private constructor(private val text: String) {
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
      return HtmlTemplate(TextResource(path).content)
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
