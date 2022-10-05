package io.redgreen.tumbleweed

import java.util.regex.Pattern

@JvmInline
value class MethodDescriptor(private val value: String) {
  val returnType: String
    get() {
      val typeDescriptor = if (hasNoParameters) {
        value.replace("()", "")
      } else {
        value.substring(value.indexOf(")") + 1)
      }

      return TypeToken(typeDescriptor).type
    }

  val parameters: List<String>
    get() {
      if (hasNoParameters) {
        return emptyList()
      }

      val pattern = Pattern.compile("\\(.+\\)")
      val matcher = pattern.matcher(value)
      return if (matcher.find()) {
        matcher.group(0)
          .replace("(", "")
          .replace(")", "")
          .split(";")
          .map { it.removePrefix("L") }
          .filter { it.isNotBlank() }
          .map { it.replace("/", ".") }
      } else {
        emptyList()
      }
    }

  private val hasNoParameters: Boolean
    get() = value.contains("()")
}
