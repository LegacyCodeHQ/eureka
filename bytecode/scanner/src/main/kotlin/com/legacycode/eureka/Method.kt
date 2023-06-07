package com.legacycode.eureka

data class Method(
  override val name: String,
  val descriptor: MethodDescriptor,
  override val owner: QualifiedType,
) : Member {
  val isLambda: Boolean
    get() = name.startsWith("lambda\$") || /* Java */
      name.contains("\$lambda-") || /* Kotlin 1.7.10 */
      name.contains("\$lambda\$") /* Kotlin 1.7.20 */

  val isBridge: Boolean
    get() = name.startsWith("access\$") /* Kotlin */

  val isCopyConstructor: Boolean
    get() = name.startsWith("copy\$default") /* Kotlin */

  val isSynthetic: Boolean
    get() = isLambda || isBridge || isCopyConstructor

  override val signature: Signature
    get() = MethodSignature.from(
      name,
      descriptor.parameters,
      descriptor.returnType,
    )
}
