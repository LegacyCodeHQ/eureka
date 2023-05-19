package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.ClassStructure
import com.legacycode.tumbleweed.Member
import com.legacycode.tumbleweed.Relationship
import com.legacycode.tumbleweed.web.observablehq.classifiers.MemberClassifier

class Transformer(
  private val classifier: MemberClassifier,
) {
  fun transform(classStructure: ClassStructure): BilevelEdgeBundlingGraph {
    val nodes = (classStructure.fields + classStructure.methods).map(::toNode)
    val links = classStructure.relationships.map(::toLink)
    return BilevelEdgeBundlingGraph(nodes, links, classStructure.classInfoMap())
  }

  private fun toNode(member: Member): BilevelEdgeBundlingGraph.Node {
    val group = classifier.groupOf(member)

    return BilevelEdgeBundlingGraph.Node(
      id = member.signature.concise,
      group = group
    )
  }

  private fun toLink(relationship: Relationship): BilevelEdgeBundlingGraph.Link {
    return BilevelEdgeBundlingGraph.Link(
      source = relationship.source.signature.concise,
      target = relationship.target.signature.concise
    )
  }

  private fun ClassStructure.classInfoMap(): Map<String, Map<String, Any>> {
    val classInfo = mutableMapOf<String, Any>(
      "name" to type.name.replace("/", "."),
    ).apply {
      if (superType.name != "java.lang.Object") {
        put("extends", superType.name.replace("/", "."))
      }
      if (interfaces.isNotEmpty()) {
        put("implements", interfaces.map { it.name.replace("/", ".") })
      }
    }
    return mapOf("classInfo" to classInfo.toMap())
  }
}
