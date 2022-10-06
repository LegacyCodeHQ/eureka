package io.redgreen.tumbleweed

data class Relationship(
  val method: Method,
  val field: Field,
  val type: Type,
) {
  enum class Type {
    Reads, Writes,
  }
}
