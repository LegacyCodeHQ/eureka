package com.legacycode.eureka

data class Field(
  override val name: String,
  val descriptor: FieldDescriptor,
  override val owner: QualifiedType,
) : Member {
  override val signature: Signature
    get() = FieldSignature(name, QualifiedType(descriptor.type))
}
