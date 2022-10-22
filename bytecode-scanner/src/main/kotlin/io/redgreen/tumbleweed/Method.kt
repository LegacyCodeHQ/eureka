package io.redgreen.tumbleweed

data class Method(
  override val name: String,
  val descriptor: MethodDescriptor,
  override val owner: String,
) : Member {
  val isLambda: Boolean
    get() = name.startsWith("lambda\$") || /* Java */
      name.contains("\$lambda-") || /* Kotlin 1.7.10 */
      name.contains("\$lambda\$") /* Kotlin 1.7.20 */

  val isBridge: Boolean
    get() = name.startsWith("access\$") /* Kotlin */

  val isSynthetic: Boolean
    get() = isLambda || isBridge

  override val signature: Signature
    get() = MethodSignature(
      name,
      descriptor.parameters,
      descriptor.returnType,
    )
}
