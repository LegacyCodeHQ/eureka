package com.legacycode.eureka.dex

import java.util.regex.Pattern

sealed class SearchTerm(open val text: String) {
  companion object {
    private const val DIRECTIVE_EXACT = "exact:"
    private const val DIRECTIVE_REGEX = "regex:"

    fun from(text: String): SearchTerm {
      return if (text.startsWith(DIRECTIVE_REGEX, true)) {
        RegexSearchTerm(text.substring(DIRECTIVE_REGEX.length))
      } else if (text.startsWith(DIRECTIVE_EXACT, true)) {
        val keyword = text.substring(DIRECTIVE_EXACT.length)
        ExactSearchTerm(keyword)
      } else {
        DefaultSearchTerm(text)
      }
    }
  }

  abstract fun matches(simpleClassName: String): Boolean

  class DefaultSearchTerm(override val text: String) : SearchTerm(text) {
    override fun matches(simpleClassName: String): Boolean {
      if (simpleClassName.contains('$')) {
        val nestedSimpleClassName = simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
        return nestedSimpleClassName.contains(text, true)
      }

      return simpleClassName.contains(text, true)
    }
  }

  class ExactSearchTerm(override val text: String) : SearchTerm(text) {
    override fun matches(simpleClassName: String): Boolean {
      val maybeWordsWithHyphenation = simpleClassName.split("(?<=.)(?=\\p{Lu})".toRegex())
      val words = maybeWordsWithHyphenation.flatMap { it.split("_", "-") }
      return words.any { word -> word.equals(text, true) }
    }
  }

  class RegexSearchTerm(override val text: String) : SearchTerm(text) {
    override fun matches(simpleClassName: String): Boolean {
      val pattern = Pattern.compile(text)
      val matcher = pattern.matcher(simpleClassName)
      return matcher.find()
    }
  }
}
