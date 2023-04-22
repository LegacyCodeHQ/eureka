package com.legacycode.tumbleweed

data class Field constructor(
  override val name: String,
  val descriptor: FieldDescriptor,
  override val owner: QualifiedType,
) : Member {
  override val signature: Signature
    get() = FieldSignature(name, QualifiedType(descriptor.type))
}
