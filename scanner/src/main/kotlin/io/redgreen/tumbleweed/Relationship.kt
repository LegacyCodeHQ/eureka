package io.redgreen.tumbleweed

data class Relationship(
  val method: Method,
  val field: Field,
  val type: Type,
) {
  companion object {
    fun reads(method: Method, field: Field): Relationship =
      Relationship(method, field, Type.Reads)

    fun writes(method: Method, field: Field): Relationship =
      Relationship(method, field, Type.Writes)
  }

  enum class Type {
    Reads, Writes,
  }
}
