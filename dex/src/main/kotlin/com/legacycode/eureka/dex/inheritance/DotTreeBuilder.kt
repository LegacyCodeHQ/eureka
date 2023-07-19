package com.legacycode.eureka.dex.inheritance

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child

class DotTreeBuilder(private val title: String) : AdjacencyList.TreeBuilder<String> {
  private val stringBuilder = StringBuilder()
  private lateinit var ancestor: Ancestor

  override val out: String
    get() = stringBuilder.toString()

  override fun beforeTraversal() {
    stringBuilder.appendLine("digraph \"$title\" {")
  }

  override fun visitAncestor(ancestor: Ancestor) {
    this.ancestor = ancestor
  }

  override fun visitChild(child: Child) {
    stringBuilder.appendLine("  \"${child.fqn}\" -> \"${ancestor.fqn}\"")
  }

  override fun afterTraversal() {
    stringBuilder.appendLine("}")
  }
}
