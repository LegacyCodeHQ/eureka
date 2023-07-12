package com.legacycode.eureka.dex

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

class TreeClusterJsonTreeBuilder : InheritanceRegister.TreeBuilder<String> {
  companion object {
    private const val KEY_NAME = "name"
    private const val KEY_CHILDREN = "children"
  }

  private val objectMapper = ObjectMapper()
  private val rootNode = objectMapper.createObjectNode()
  private val allNodes = mutableSetOf<ObjectNode>()
  private lateinit var currentAncestor: ObjectNode

  override val out: String
    get() = objectMapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(rootNode)

  override fun visitAncestor(ancestor: Ancestor) {
    currentAncestor = if (::currentAncestor.isInitialized) {
      allNodes.find { it.get(KEY_NAME).asText() == ancestor.fqn } ?: objectMapper.createObjectNode()
    } else {
      rootNode
    }
    allNodes.add(currentAncestor)

    currentAncestor.put(KEY_NAME, ancestor.fqn)
  }

  override fun visitChild(child: Child) {
    val hasChildren = currentAncestor.has(KEY_CHILDREN)
    val arrayNode = if (hasChildren) {
      currentAncestor.get(KEY_CHILDREN) as ArrayNode
    } else {
      val newArrayNode = objectMapper.createArrayNode()
      currentAncestor.set<ArrayNode>(KEY_CHILDREN, newArrayNode)
      newArrayNode
    }

    val newChildNode = objectMapper.createObjectNode()
    newChildNode.put(KEY_NAME, child.fqn)
    arrayNode.add(newChildNode)

    allNodes.add(newChildNode)
  }
}
