package io.redgreen.tumbleweed.web.observablehq

data class BilevelEdgeBundlingGraph(
  val nodes: List<Node>,
  val links: List<Link>,
) {
  companion object {
    // placeholder for extension functions
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
