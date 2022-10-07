package io.redgreen.tumbleweed

@JvmInline
value class TypeToken(private val value: String) {
  val type: String
    get() = when (value) {
      "V" -> "void"
      "Z" -> "boolean"
      "B" -> "byte"
      "S" -> "short"
      "C" -> "char"
      "I" -> "int"
      "J" -> "long"
      "F" -> "float"
      "D" -> "double"
      else -> {
        val sanitizedType = value
          .removeSuffix(";")
          .replace("/", ".")

        if (sanitizedType.startsWith("[")) {
          "[]${sanitizedType.drop(2)}"
        } else {
          sanitizedType.removePrefix("L")
        }
      }
    }
}
