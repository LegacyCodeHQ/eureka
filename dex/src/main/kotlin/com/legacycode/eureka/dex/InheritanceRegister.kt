package com.legacycode.eureka.dex

class InheritanceRegister {
  interface TreeBuilder<T> {
    val out: T

    fun beforeTraversal() {
      // no-op
    }

    fun visitAncestor(ancestor: Ancestor)
    fun visitChild(child: Child)

    fun afterTraversal() {
      // no-op
    }
  }

  val isEmpty: Boolean
    get() = parentChildrenMap.isEmpty()

  private val parentChildrenMap: MutableMap<Ancestor, MutableSet<Child>> = mutableMapOf()

  fun add(ancestor: Ancestor, child: Child) {
    var children = parentChildrenMap[ancestor]
    if (children == null) {
      children = mutableSetOf()
      parentChildrenMap[ancestor] = children
    }
    children.add(child)
  }

  fun ancestors(): Set<String> {
    return parentChildrenMap.keys.map { it.id }.toSet()
  }

  fun children(ancestor: Ancestor): Set<Child> {
    return parentChildrenMap[ancestor]?.toSet() ?: emptySet()
  }

  fun <T> tree(root: Ancestor, treeBuilder: TreeBuilder<T>): T {
    var children = parentChildrenMap[root]
    treeBuilder.beforeTraversal()
    treeBuilder.visitAncestor(root)

    val queue = ArrayDeque<Ancestor>()
    while (children != null) {
      for (child in children.iterator()) {
        treeBuilder.visitChild(child)
        val potentialAncestor = child.asAncestor()
        if (parentChildrenMap.contains(potentialAncestor)) {
          queue.add(potentialAncestor)
        }
      }
      val ancestor = queue.removeFirstOrNull()
      children = if (ancestor != null) {
        treeBuilder.visitAncestor(ancestor)
        parentChildrenMap[ancestor]
      } else {
        null
      }
    }
    treeBuilder.afterTraversal()

    return treeBuilder.out
  }
}
