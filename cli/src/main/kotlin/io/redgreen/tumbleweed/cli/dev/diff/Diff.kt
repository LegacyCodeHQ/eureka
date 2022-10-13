package io.redgreen.tumbleweed.cli.dev.diff

import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Node

data class Diff(val missing: Missing) {
  companion object {
    fun of(
      baseline: BilevelEdgeBundlingGraph,
      implementation: BilevelEdgeBundlingGraph,
    ): Diff {
      val missingNodes = baseline.nodes - implementation.nodes.toSet()
      return Diff(Missing(missingNodes))
    }
  }
}

data class Missing(
  val nodes: List<Node>,
)

val Diff.printable: String
  get() {
    return "✅ All good, no differences found."
  }
