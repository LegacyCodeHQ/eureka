package io.redgreen.tumbleweed

data class Method(
  val name: String,
  val descriptor: MethodDescriptor,
) : Member {
  val isLambda: Boolean
    get() = name.contains("\$lambda-") || /* Kotlin 1.7.10 */
      name.contains("\$lambda\$") /* Kotlin 1.7.20 */

  override val signature: String
    get() = "${descriptor.returnType} ${name}(${descriptor.parameters.joinToString(", ")})"
}
