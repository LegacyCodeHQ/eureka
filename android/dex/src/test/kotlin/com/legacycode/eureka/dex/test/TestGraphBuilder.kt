package com.legacycode.eureka.dex.test

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child

class TestGraphBuilder : AdjacencyList.GraphBuilder<String> {
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
