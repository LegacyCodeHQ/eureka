package com.legacycode.eureka

@JvmInline
value class TypeToken(private val descriptor: String) {
  companion object {
    fun isPrimitive(char: String): Boolean =
      char in listOf("B", "C", "D", "F", "I", "J", "S", "Z", "V")

    fun isObject(char: String): Boolean =
      char == "L"

    fun isArray(char: String): Boolean =
      char == "["
  }

  val type: String
    get() = if (descriptor.length == 1) {
      primitiveType(descriptor)
    } else {
      arrayOrObjectType(descriptor)
    }

  private fun primitiveType(descriptor: String): String {
    return when (descriptor) {
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
        throw IllegalArgumentException("Unknown type: $descriptor")
      }
    }
  }

  private fun arrayOrObjectType(descriptor: String): String {
    val sanitizedType = descriptor
      .removeSuffix(";")
      .replace("/", ".")

    return if (sanitizedType.startsWith("[L")) {
      "[]${sanitizedType.drop(2)}"
    } else if (sanitizedType.startsWith("[")) {
      "[]${primitiveType(sanitizedType.drop(1))}"
    } else {
      sanitizedType.removePrefix("L")
    }
  }
}
