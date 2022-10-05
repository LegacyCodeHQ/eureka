package io.redgreen.tumbleweed

data class ClassStructure(
  val `package`: String,
  val className: String,
  val fields: List<Field>,
)
