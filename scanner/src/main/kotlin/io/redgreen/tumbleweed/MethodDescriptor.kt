package io.redgreen.tumbleweed

import java.util.regex.Pattern

@JvmInline
value class MethodDescriptor(private val value: String) {
  val returnType: String
    get() {
      val typeDescriptor = if (value.contains("()")) {
        value.replace("()", "")
      } else {
        value.substring(value.indexOf(")") + 1)
      }

      return when (typeDescriptor) {
        "V" -> "void"
        "Z" -> "boolean"
        "B" -> "byte"
        "S" -> "short"
        "C" -> "char"
        "I" -> "int"
        "J" -> "long"
        "F" -> "float"
        "D" -> "double"
        else -> typeDescriptor
          .removePrefix("L")
          .removeSuffix(";")
          .replace("/", ".")
      }
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
