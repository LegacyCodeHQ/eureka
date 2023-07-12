package com.legacycode.eureka.dex

sealed class Node(open val id: String) {
  val fqn: String
    get() {
      return id.replace('/', '.').drop(1).dropLast(1)
    }
}

data class Child(override val id: String) : Node(id) {
  fun asAncestor(): Ancestor {
    return Ancestor(id)
  }
}

data class Ancestor(override val id: String) : Node(id)
