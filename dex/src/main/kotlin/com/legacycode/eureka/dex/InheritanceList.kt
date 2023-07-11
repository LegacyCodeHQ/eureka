package com.legacycode.eureka.dex

class InheritanceList {
  val isEmpty: Boolean get() = true

  private val parentChildrenMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

  fun add(child: String, ancestor: String) {
    var children = parentChildrenMap[ancestor]
    if (children == null) {
      children = mutableSetOf()
      parentChildrenMap[ancestor] = children
    }
    children.add(child)
  }

  fun ancestors(): Set<String> {
    return parentChildrenMap.keys.toSet()
  }

  fun children(ancestor: String): Set<String> {
    return parentChildrenMap[ancestor]!!
  }
}
