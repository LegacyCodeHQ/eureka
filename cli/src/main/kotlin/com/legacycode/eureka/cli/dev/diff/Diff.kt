package com.legacycode.eureka.cli.dev.diff

import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph
import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph.Link
import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph.Node

data class Diff(val missing: Missing) {
  companion object {
    fun of(
      baseline: EdgeBundlingGraph,
      implementation: EdgeBundlingGraph,
    ): Diff {
      val missingNodes = baseline.nodes - implementation.nodes.toSet()
      val missingLinks = baseline.links - implementation.links.toSet()
      return Diff(Missing(missingNodes, missingLinks))
    }
  }
}

data class Missing(
  val nodes: List<Node>,
  val links: List<Link>,
)

val Diff.report: String
  get() {
    val (nodes, links) = missing
    return if (nodes.isEmpty()) {
      "✅ All good, no differences found."
    } else {
      """
        |❌ Missing nodes (${nodes.count()}):
        |${nodes.joinToString(separator = System.lineSeparator()) { "  - (group: ${it.group}) ${it.id}" }}
        |""".trimMargin() +
        """
        |❌ Missing links (${links.count()}):
        |${links.joinToString(separator = System.lineSeparator()) { "  - ${it.source} -> ${it.target}" }}
        |""".trimMargin()
    }
  }
