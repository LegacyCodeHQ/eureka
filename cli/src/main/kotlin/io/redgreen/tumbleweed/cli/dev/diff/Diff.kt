package io.redgreen.tumbleweed.cli.dev.diff

import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Link
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Node

data class Diff(val missing: Missing) {
  companion object {
    fun of(
      baseline: BilevelEdgeBundlingGraph,
      implementation: BilevelEdgeBundlingGraph,
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
