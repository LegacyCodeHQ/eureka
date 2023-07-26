package com.legacycode.eureka.dex.dependency

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child
import com.legacycode.eureka.dex.Node

class D3GraphBuilder : AdjacencyList.GraphBuilder<String> {
  companion object {
    private const val DEFAULT_PACKAGE_NAME = ""
  }

  private val objectMapper = ObjectMapper()
  private val rootNode = objectMapper.createObjectNode()
  private val linksArray = objectMapper.createArrayNode()
  private val allNodes = mutableSetOf<String>()

  private lateinit var currentNode: Node

  override val out: String
    get() = objectMapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(rootNode)

  override fun visitAncestor(ancestor: Ancestor) {
    allNodes.add(ancestor.fqn)
    currentNode = ancestor
  }

  override fun visitChild(child: Child) {
    allNodes.add(child.fqn)

    val route = objectMapper.createObjectNode().apply {
      put("source", currentNode.fqn)
      put("target", child.fqn)
    }
    linksArray.add(route)
  }

  override fun afterTraversal() {
    val nodesArray = objectMapper.createArrayNode()
    allNodes.onEach { fqn ->
      val objectNode = objectMapper.createObjectNode().apply {
        put("id", fqn)
        put("group", getPackageName(fqn))
      }
      nodesArray.add(objectNode)
    }

    rootNode.set<ArrayNode>("nodes", nodesArray)
    rootNode.set<ArrayNode>("links", linksArray)
  }

  private fun getPackageName(fqn: String): String {
    val isInDefaultPackage = !fqn.contains(".")
    val packageName = if (isInDefaultPackage) {
      DEFAULT_PACKAGE_NAME
    } else {
      fqn.substring(0, fqn.lastIndexOf('.'))
    }
    return packageName
  }
}
