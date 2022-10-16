package io.redgreen.tumbleweed

data class Field(
  val name: String,
  val descriptor: FieldDescriptor,
) : Member {
  override val signature: Signature
    get() = FieldSignature(name, descriptor.type)
}
