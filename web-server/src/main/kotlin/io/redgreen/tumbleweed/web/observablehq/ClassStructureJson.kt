package io.redgreen.tumbleweed.web.observablehq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.redgreen.tumbleweed.ClassStructure
import io.redgreen.tumbleweed.Field
import io.redgreen.tumbleweed.Member
import io.redgreen.tumbleweed.Method
import io.redgreen.tumbleweed.Relationship

val ClassStructure.json: String
  get() {
    val nodes = (this.fields + this.methods).map(Member::toNode)
    val links = relationships.map(Relationship::toLink)

    val bilevelEdgeBundlingGraph = BilevelEdgeBundlingGraph(nodes, links)

    return jacksonObjectMapper()
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(bilevelEdgeBundlingGraph)
  }

private fun Member.toNode(): BilevelEdgeBundlingGraph.Node {
  val group = when (this) {
    is Field -> 1
    is Method -> 2
  }

  return BilevelEdgeBundlingGraph.Node(
    id = signature,
    group = group
  )
}

private fun Relationship.toLink(): BilevelEdgeBundlingGraph.Link {
  return BilevelEdgeBundlingGraph.Link(
    source = source.signature,
    target = target.signature
  )
}
