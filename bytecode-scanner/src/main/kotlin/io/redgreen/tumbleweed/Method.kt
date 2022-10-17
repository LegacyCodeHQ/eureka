package io.redgreen.tumbleweed

data class Method(
  val name: String,
  val descriptor: MethodDescriptor,
) : Member {
  val isLambda: Boolean
    get() = name.startsWith("lambda\$") || /* Java */
      name.contains("\$lambda-") || /* Kotlin 1.7.10 */
      name.contains("\$lambda\$") /* Kotlin 1.7.20 */

  val isBridge: Boolean
    get() = name.startsWith("access\$") /* Kotlin */

  override val signature: Signature
    get() = MethodSignature(
      name,
      descriptor.parameters,
      descriptor.returnType,
    )
}
