package com.legacycode.tumbleweed

sealed interface Member {
  val name: String
  val signature: Signature
  val owner: QualifiedType
}
