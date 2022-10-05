package io.redgreen.tumbleweed

data class Field(
  val name: String,
  val descriptor: Descriptor,
) {
  val type: String
    get() = when (descriptor.value) {
      "V" -> "void"
      "Z" -> "boolean"
      "B" -> "byte"
      "S" -> "short"
      "C" -> "char"
      "I" -> "int"
      "J" -> "long"
      "F" -> "float"
      "D" -> "double"
      else -> descriptor.value
        .removePrefix("L")
        .removeSuffix(";")
        .replace("/", ".")
    }
}
