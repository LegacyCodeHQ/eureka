package com.legacycode.eureka.dex.dependency

import com.fasterxml.jackson.databind.ObjectMapper
import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child
import com.legacycode.eureka.dex.Node

class OntologyGraphBuilder : AdjacencyList.GraphBuilder<String> {
  private val objectMapper = ObjectMapper()
  private val rootNode = objectMapper.createArrayNode()
  private lateinit var currentNode: Node

  override val out: String
    get() = objectMapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(rootNode)

  override fun visitAncestor(ancestor: Ancestor) {
    currentNode = ancestor
  }

  override fun visitChild(child: Child) {
    val route = objectMapper.createObjectNode().apply {
      put("source", currentNode.fqn)
      put("target", child.fqn)
    }

    rootNode.add(route)
  }
}
