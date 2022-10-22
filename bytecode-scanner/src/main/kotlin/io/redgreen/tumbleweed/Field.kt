package io.redgreen.tumbleweed

data class Field constructor(
  override val name: String,
  val descriptor: FieldDescriptor,
  override val owner: String,
) : Member {
  override val signature: Signature
    get() = FieldSignature(name, descriptor.type)
}
