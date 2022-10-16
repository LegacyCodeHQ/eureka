package io.redgreen.tumbleweed

sealed interface Signature {
  val verbose: String
}

data class FieldSignature(
  val name: String,
  val type: String,
) : Signature {
  override val verbose: String
    get() = "$type $name"
}

data class MethodSignature(
  val name: String,
  val parameters: List<String>,
  val returnType: String,
) : Signature {
  override val verbose: String
    get() = "$returnType ${name}(${parameters.joinToString(", ")})"
}
