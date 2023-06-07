package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.ClassStructure
import com.legacycode.eureka.Member
import com.legacycode.eureka.Relationship

class Transformer(
  private val classifier: MemberClassifier,
) {
  fun transform(classStructure: ClassStructure): EdgeBundlingGraph {
    val nodes = (classStructure.fields + classStructure.methods).map(::toNode)
    val links = classStructure.relationships.map(::toLink)
    return EdgeBundlingGraph(nodes, links, classStructure.classInfoMap())
  }

  private fun toNode(member: Member): EdgeBundlingGraph.Node {
    val group = classifier.groupOf(member)

    return EdgeBundlingGraph.Node(
      id = member.signature.concise,
      group = group
    )
  }

  private fun toLink(relationship: Relationship): EdgeBundlingGraph.Link {
    return EdgeBundlingGraph.Link(
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
