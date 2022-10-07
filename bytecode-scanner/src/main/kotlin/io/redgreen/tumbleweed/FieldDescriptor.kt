package io.redgreen.tumbleweed

@JvmInline
value class FieldDescriptor(private val typeToken: TypeToken) {
  companion object {
    fun from(descriptor: String): FieldDescriptor =
        FieldDescriptor(TypeToken(descriptor))
  }

  val type: String
    get() = typeToken.type
}
