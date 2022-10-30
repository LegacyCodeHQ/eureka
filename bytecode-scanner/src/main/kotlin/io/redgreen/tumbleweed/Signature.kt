package io.redgreen.tumbleweed

sealed interface Signature {
  val name: String
  val verbose: String
  val concise: String
}

data class FieldSignature(
  override val name: String,
  val type: QualifiedType,
) : Signature {
  override val verbose: String
    get() = "${type.name} $name"

  override val concise: String
    get() = "${type.simpleName} $name"
}

data class MethodSignature(
  override val name: String,
  val parameterTypes: List<QualifiedType>,
  val returnType: QualifiedType,
) : Signature {
  companion object {
    fun from(
      name: String,
      parametersDescriptors: List<String>,
      returnTypeDescriptor: String,
    ): Signature {
      return MethodSignature(
        name,
        parametersDescriptors.map(::QualifiedType),
        QualifiedType(returnTypeDescriptor),
      )
    }
  }

  override val verbose: String
    get() = "${returnType.name} ${name}(${parameterTypes.joinToString(", ") { it.name }})"

  override val concise: String
    get() = "${returnType.simpleName} ${name}(${parameterTypes.joinToString(", ") { it.simpleName }})"
}
