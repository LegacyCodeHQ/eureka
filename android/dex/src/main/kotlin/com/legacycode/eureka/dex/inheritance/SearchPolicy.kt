package com.legacycode.eureka.dex.inheritance

import java.util.regex.Pattern

sealed class SearchPolicy(open val text: String) {
  companion object {
    private const val DIRECTIVE_EXACT = "exact:"
    private const val DIRECTIVE_REGEX = "regex:"

    fun from(text: String): SearchPolicy {
      return if (hasRegexDirective(text)) {
        RegexSearchPolicy(text.substring(DIRECTIVE_REGEX.length))
      } else if (hasExactDirective(text)) {
        val keyword = text.substring(DIRECTIVE_EXACT.length)
        ExactSearchPolicy(keyword)
      } else {
        DefaultSearchPolicy(text)
      }
    }

    private fun hasExactDirective(text: String): Boolean =
      text.startsWith(DIRECTIVE_EXACT, true)

    private fun hasRegexDirective(text: String): Boolean =
      text.startsWith(DIRECTIVE_REGEX, true)
  }

  abstract fun matches(simpleClassName: String): Boolean

  class DefaultSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      val isInnerClass = simpleClassName.contains('$')
      if (isInnerClass) {
        val innerClassName = simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
        return innerClassName.contains(text, true)
      }

      return simpleClassName.contains(text, true)
    }
  }

  class ExactSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      val isInnerClass = simpleClassName.contains('$')
      val classNameToUse = if (isInnerClass) {
        simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
      } else {
        simpleClassName
      }
      val maybeWordsWithHyphenation = classNameToUse.split("(?<=.)(?=\\p{Lu})".toRegex())
      val words = maybeWordsWithHyphenation.flatMap { it.split("_", "-") }
      return words.any { word -> word.equals(text, true) }
    }
  }

  class RegexSearchPolicy(override val text: String) : SearchPolicy(text) {
    override fun matches(simpleClassName: String): Boolean {
      val isInnerClass = simpleClassName.contains('$')
      val classNameToUse = if (isInnerClass) {
        simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
      } else {
        simpleClassName
      }
      val pattern = Pattern.compile(text)
      val matcher = pattern.matcher(classNameToUse)
      return matcher.find()
    }
  }
}
