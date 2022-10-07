package io.redgreen.tumbleweed

data class Method(
  val name: String,
  val descriptor: MethodDescriptor,
) : Member {
  override val signature: String
    get() = "${descriptor.returnType} ${name}(${descriptor.parameters.joinToString(", ")})"
}
