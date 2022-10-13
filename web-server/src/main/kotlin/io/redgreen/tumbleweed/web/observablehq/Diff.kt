package io.redgreen.tumbleweed.web.observablehq

import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Node

data class Diff(val missing: Missing) {
  companion object {
    fun from(missingNodes: List<Node>): Diff {
      return Diff(Missing(missingNodes))
    }
  }

  fun isEmpty(): Boolean {
    return true
  }
}

data class Missing(
  val nodes: List<Node>,
)
