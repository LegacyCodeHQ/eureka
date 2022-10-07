package io.redgreen.tumbleweed

data class Field(
  val name: String,
  val descriptor: FieldDescriptor,
) : Member {
  override val signature: String
    get() = "${descriptor.type} $name"
}