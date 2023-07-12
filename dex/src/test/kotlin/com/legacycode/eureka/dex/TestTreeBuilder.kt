package com.legacycode.eureka.dex

class TestTreeBuilder : InheritanceAdjacencyList.TreeBuilder<String> {
  private val builder = StringBuilder()
  private lateinit var ancestor: Ancestor

  override val out: String
    get() = builder.toString()

  override fun visitAncestor(ancestor: Ancestor) {
    this.ancestor = ancestor
  }

  override fun visitChild(child: Child) {
    builder.append("${child.id} -> ${ancestor.id}")
      .appendLine()
  }
}
