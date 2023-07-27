package com.legacycode.eureka.dex

import com.legacycode.eureka.dex.inheritance.SearchPolicy

class AdjacencyList {
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

  interface GraphBuilder<T> {
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

  fun ancestors(): Set<Ancestor> {
    return parentChildrenMap.keys.toSet()
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

  fun prune(keyword: String): AdjacencyList {
    val adjacencyList = AdjacencyList()
    val searchPolicy = SearchPolicy.from(keyword)

    fun dfs(ancestor: Ancestor): Boolean {
      val children = children(ancestor)
      var shouldAdd = children.any { child ->
        val descriptor = Descriptor.from(child.id)
        searchPolicy.matches(descriptor.simpleClassName)
      }

      val relevantChildren = mutableListOf<Child>()

      for (child in children) {
        val childAsAncestor = child.asAncestor()
        if (dfs(childAsAncestor)) {
          shouldAdd = true
          relevantChildren.add(child)
        }
      }

      if (shouldAdd) {
        for (child in relevantChildren) {
          adjacencyList.add(ancestor, child)
        }
      }

      return shouldAdd || searchPolicy.matches(Descriptor.from(ancestor.id).simpleClassName)
    }

    val rootAncestors = ancestors()
    for (rootAncestor in rootAncestors) {
      dfs(rootAncestor)
    }

    return adjacencyList
  }

  fun contains(id: String): Boolean {
    return parentChildrenMap
      .entries
      .any { (ancestor, children) ->
        ancestor.id == id || children.any { it.id == id }
      }
  }

  fun prune(node: Node): AdjacencyList {
    val adjacencyList = AdjacencyList()
    val queue = ArrayDeque<Ancestor>()
    val startNode = Ancestor(node.id)

    if (parentChildrenMap.containsKey(startNode)) {
      queue.add(startNode)
    }

    while (queue.isNotEmpty()) {
      val current = queue.removeFirst()
      val children = parentChildrenMap[current]

      if (children != null) {
        for (child in children) {
          adjacencyList.add(current, child)
          val childAsAncestor = child.asAncestor()
          if (parentChildrenMap.containsKey(childAsAncestor)) {
            queue.add(childAsAncestor)
          }
        }
      }
    }

    return adjacencyList
  }

  fun <T> graph(graphBuilder: GraphBuilder<T>): T {
    graphBuilder.beforeTraversal()

    val visited = mutableSetOf<Ancestor>()
    val queue = ArrayDeque<Ancestor>(ancestors())

    while (queue.isNotEmpty()) {
      val currentAncestor = queue.removeFirst()

      if (currentAncestor in visited) continue
      visited.add(currentAncestor)

      graphBuilder.visitAncestor(currentAncestor)

      val children = parentChildrenMap[currentAncestor]

      if (children != null) {
        for (child in children) {
          graphBuilder.visitChild(child)
          val childAsAncestor = child.asAncestor()
          if (childAsAncestor !in visited) {
            queue.add(childAsAncestor)
          }
        }
      }
    }

    graphBuilder.afterTraversal()

    return graphBuilder.out
  }
}
