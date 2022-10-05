package io.redgreen.tumbleweed

@JvmInline
value class Type(private val descriptor: String) {
  val name: String
    get() {
      return when (descriptor.replace("()", "")) {
        "V" -> "void"
        "Z" -> "boolean"
        "B" -> "byte"
        "S" -> "short"
        "C" -> "char"
        "I" -> "int"
        "J" -> "long"
        "F" -> "float"
        "D" -> "double"
        else -> descriptor
          .replace("()", "")
          .removePrefix("L")
          .removeSuffix(";")
          .replace("/", ".")
      }
    }
}
