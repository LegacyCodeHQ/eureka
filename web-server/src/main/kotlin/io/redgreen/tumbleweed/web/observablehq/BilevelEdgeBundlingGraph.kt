package io.redgreen.tumbleweed.web.observablehq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class BilevelEdgeBundlingGraph(
  val nodes: List<Node>,
  val links: List<Link>,
) {
  companion object

  fun asJson(): String {
    return jacksonObjectMapper()
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(this)
  }

  operator fun minus(other: BilevelEdgeBundlingGraph): Diff {
    val missingNodes = nodes - other.nodes.toSet()
    return Diff.from(missingNodes)
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
