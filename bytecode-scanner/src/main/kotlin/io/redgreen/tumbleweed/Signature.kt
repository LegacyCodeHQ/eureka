package io.redgreen.tumbleweed

typealias FullTypeName = String

sealed interface Signature {
  val verbose: String
}

data class FieldSignature(
  val name: String,
  val type: FullTypeName,
) : Signature {
  override val verbose: String
    get() = "$type $name"
}

data class MethodSignature(
  val name: String,
  val parameters: List<FullTypeName>,
  val returnType: FullTypeName,
) : Signature {
  override val verbose: String
    get() = "$returnType ${name}(${parameters.joinToString(", ")})"
}

val String.simpleName: String
  get() {
    val lastDotIndex = lastIndexOf(".")
    return if (lastDotIndex == -1) {
      this
    } else {
      substring(lastDotIndex + 1)
    }
  }
