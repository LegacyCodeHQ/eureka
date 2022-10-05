package io.redgreen.tumbleweed

@JvmInline
value class FieldDescriptor(private val value: String) {
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
      else -> value
        .removePrefix("L")
        .removeSuffix(";")
        .replace("/", ".")
    }
}
