package com.legacycode.eureka.dex.dependency

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child

class DotGraphBuilder(private val title: String) : AdjacencyList.GraphBuilder<String> {
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
