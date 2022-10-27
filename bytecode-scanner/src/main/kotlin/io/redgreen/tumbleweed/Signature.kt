package io.redgreen.tumbleweed

typealias QualifiedTypeName = String

sealed interface Signature {
  val name: String
  val verbose: String
  val concise: String
}

data class FieldSignature(
  override val name: String,
  val type: QualifiedTypeName,
) : Signature {
  override val verbose: String
    get() = "$type $name"

  override val concise: String
    get() = "${type.simpleName} $name"
}

data class MethodSignature(
  override val name: String,
  val parameterTypes: List<QualifiedTypeName>,
  val returnType: QualifiedTypeName,
) : Signature {
  override val verbose: String
    get() = "$returnType ${name}(${parameterTypes.joinToString(", ")})"

  override val concise: String
    get() = "${returnType.simpleName} ${name}(${parameterTypes.joinToString(", ") { it.simpleName }})"
}

val QualifiedTypeName.simpleName: String
  get() {
    val isArray = this.startsWith("[]")
    val lastDotIndex = lastIndexOf(".")
    val isPrimitive = lastDotIndex == -1

    val simpleName = if (isPrimitive) {
      this
    } else {
      val possiblySimpleName = substring(lastDotIndex + 1)
      val lastDollarIndex = possiblySimpleName.lastIndexOf('$')
      if (lastDollarIndex == -1) possiblySimpleName else possiblySimpleName.substring(lastDollarIndex + 1)
    }

    return if (isPrimitive && isArray) {
      simpleName
    } else if (isArray) {
      this.substring(0, this.lastIndexOf("]") + 1) + simpleName
    } else {
      simpleName
    }
  }
