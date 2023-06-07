package com.legacycode.eureka.viz.edgebundling

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory

data class EdgeBundlingGraph(
  val nodes: List<Node>,
  val links: List<Link>,
  val meta: Map<String, Any> = emptyMap(),
) {
  companion object {
    fun fromJson(json: String): EdgeBundlingGraph {
      return jacksonObjectMapper()
        .readValue(json, EdgeBundlingGraph::class.java)
    }

    fun isValidJson(json: String): Boolean {
      val logger = LoggerFactory.getLogger(EdgeBundlingGraph::class.java)
      return try {
        jacksonObjectMapper()
          .readValue(json, EdgeBundlingGraph::class.java)
        true
      } catch (e: JsonProcessingException) {
        logger.error("Failed to parse the JSON", e)
        false
      }
    }
  }

  fun toJson(): String {
    return jacksonObjectMapper()
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(this)
  }

  data class Node(
    val id: String,
    val group: Int,
  ) {
    companion object
  }

  data class Link(
    val source: String,
    val target: String,
    val value: Int = 1,
  ) {
    companion object
  }
}
