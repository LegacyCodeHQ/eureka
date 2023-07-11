package com.legacycode.eureka.dex

class InheritanceList {
  val isEmpty: Boolean get() = true

  private val parentChildrenMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

  fun add(ancestor: Ancestor, child: Child) {
    var children = parentChildrenMap[ancestor.id]
    if (children == null) {
      children = mutableSetOf()
      parentChildrenMap[ancestor.id] = children
    }
    children.add(child.id)
  }

  fun ancestors(): Set<String> {
    return parentChildrenMap.keys.toSet()
  }

  fun children(ancestor: Ancestor): Set<String> {
    return parentChildrenMap[ancestor.id] ?: emptySet()
  }
}

@JvmInline
value class Child(val id: String)

@JvmInline
value class Ancestor(val id: String)
