package com.legacycode.eureka.dex

sealed class Node(open val id: String)

data class Child(override val id: String) : Node(id) {
  fun asAncestor(): Ancestor {
    return Ancestor(id)
  }
}

data class Ancestor(override val id: String) : Node(id)
