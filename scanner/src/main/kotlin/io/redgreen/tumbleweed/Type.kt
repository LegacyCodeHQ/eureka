package io.redgreen.tumbleweed

@JvmInline
value class Type(private val descriptor: String) {
  val name: String
    get() = descriptor
      .removePrefix("L")
      .removeSuffix(";")
      .replace("/", ".")
}
