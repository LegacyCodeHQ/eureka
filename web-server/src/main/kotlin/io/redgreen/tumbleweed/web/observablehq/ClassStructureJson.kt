package io.redgreen.tumbleweed.web.observablehq

import io.redgreen.tumbleweed.ClassStructure
import io.redgreen.tumbleweed.Field
import io.redgreen.tumbleweed.Member
import io.redgreen.tumbleweed.Method
import io.redgreen.tumbleweed.Relationship

val ClassStructure.graph: BilevelEdgeBundlingGraph
  get() {
    val nodes = (fields + methods).map(Member::toNode)
    val links = relationships.map(Relationship::toLink)
    return BilevelEdgeBundlingGraph(nodes, links)
  }

private fun Member.toNode(): BilevelEdgeBundlingGraph.Node {
  val group = when (this) {
    is Field -> 1
    is Method -> 2
  }

  return BilevelEdgeBundlingGraph.Node(
    id = signature.concise,
    group = group
  )
}

private fun Relationship.toLink(): BilevelEdgeBundlingGraph.Link {
  return BilevelEdgeBundlingGraph.Link(
    source = source.signature.concise,
    target = target.signature.concise
  )
}
