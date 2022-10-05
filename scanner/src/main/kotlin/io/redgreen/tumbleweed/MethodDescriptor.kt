package io.redgreen.tumbleweed

import java.util.regex.Pattern

@JvmInline
value class MethodDescriptor(private val value: String) {
  val returnType: String
    get() {
      val zeroParameters = value.contains("()")
      val typeDescriptor = if (zeroParameters) {
        value.replace("()", "")
      } else {
        value.substring(value.indexOf(")") + 1)
      }

      return TypeToken(typeDescriptor).type
    }

  val parameters: List<String>
    get() {
      if (value.contains("()")) {
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
}
