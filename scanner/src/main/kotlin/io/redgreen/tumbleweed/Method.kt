package io.redgreen.tumbleweed

data class Method(
  val name: String,
  val descriptor: Type,
) {
  val signature: String
    get() = "${descriptor.name} $name()"
}
