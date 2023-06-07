package com.legacycode.eureka

sealed interface Member {
  val name: String
  val signature: Signature
  val owner: QualifiedType
}
