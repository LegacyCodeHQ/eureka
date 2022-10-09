package io.redgreen.tumbleweed

data class Method(
  val name: String,
  val descriptor: MethodDescriptor,
) : Member {
  val isLambda: Boolean
    get() = name.contains("\$lambda-")

  override val signature: String
    get() = "${descriptor.returnType} ${name}(${descriptor.parameters.joinToString(", ")})"
}
