package io.redgreen.tumbleweed

data class Method(
  val name: String,
) {
  val signature: String
    get() = "$name()"
}
