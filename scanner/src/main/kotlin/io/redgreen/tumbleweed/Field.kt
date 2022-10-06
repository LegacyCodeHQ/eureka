package io.redgreen.tumbleweed

data class Field(
  val name: String,
  val descriptor: FieldDescriptor,
) {
  val signature: String
    get() = "${descriptor.type} $name"
}
