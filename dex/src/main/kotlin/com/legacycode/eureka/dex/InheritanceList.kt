package com.legacycode.eureka.dex

class InheritanceList {
  val isEmpty: Boolean get() = true

  private val parentChildrenMap: MutableMap<Ancestor, MutableSet<String>> = mutableMapOf()

  fun add(ancestor: Ancestor, child: Child) {
    var children = parentChildrenMap[ancestor]
    if (children == null) {
      children = mutableSetOf()
      parentChildrenMap[ancestor] = children
    }
    children.add(child.id)
  }

  fun ancestors(): Set<String> {
    return parentChildrenMap.keys.map { it.id }.toSet()
  }

  fun children(ancestor: Ancestor): Set<String> {
    return parentChildrenMap[ancestor] ?: emptySet()
  }
}

@JvmInline
value class Child(val id: String)

@JvmInline
value class Ancestor(val id: String)
