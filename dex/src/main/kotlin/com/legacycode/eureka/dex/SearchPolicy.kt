package com.legacycode.eureka.dex

import java.util.regex.Pattern

sealed class SearchPolicy(open val text: String) {
  companion object {
    private const val DIRECTIVE_EXACT = "exact:"
    private const val DIRECTIVE_REGEX = "regex:"

    fun from(text: String): SearchPolicy {
      return if (text.startsWith(DIRECTIVE_REGEX, true)) {
        RegexSearchPolicy(text.substring(DIRECTIVE_REGEX.length))
      } else if (text.startsWith(DIRECTIVE_EXACT, true)) {
        val keyword = text.substring(DIRECTIVE_EXACT.length)
        ExactSearchPolicy(keyword)
      } else {
        DefaultSearchPolicy(text)
      }
    }
  }

  abstract fun matches(simpleClassName: String): Boolean

  class DefaultSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      if (simpleClassName.contains('$')) {
        val nestedSimpleClassName = simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
        return nestedSimpleClassName.contains(text, true)
      }

      return simpleClassName.contains(text, true)
    }
  }

  class ExactSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      val maybeWordsWithHyphenation = simpleClassName.split("(?<=.)(?=\\p{Lu})".toRegex())
      val words = maybeWordsWithHyphenation.flatMap { it.split("_", "-") }
      return words.any { word -> word.equals(text, true) }
    }
  }

  class RegexSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      val pattern = Pattern.compile(text)
      val matcher = pattern.matcher(simpleClassName)
      return matcher.find()
    }
  }
}
