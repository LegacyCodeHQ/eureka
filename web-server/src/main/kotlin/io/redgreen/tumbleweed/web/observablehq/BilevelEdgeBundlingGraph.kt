package io.redgreen.tumbleweed.web.observablehq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class BilevelEdgeBundlingGraph(
  val nodes: List<Node>,
  val links: List<Link>,
) {
  val json: String
    get() {
      return jacksonObjectMapper()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this)
    }

  data class Node(
    val id: String,
    val group: Int,
  )

  data class Link(
    val source: String,
    val target: String,
    val value: Int = 1,
  )
}
