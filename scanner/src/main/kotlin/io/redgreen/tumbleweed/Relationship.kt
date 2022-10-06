package io.redgreen.tumbleweed

data class Relationship(
  val source: Member,
  val target: Member,
  val type: Type,
) {
  enum class Type {
    Reads, Writes, Calls,
  }
}
