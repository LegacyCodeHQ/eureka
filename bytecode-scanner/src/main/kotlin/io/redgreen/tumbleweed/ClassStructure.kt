package io.redgreen.tumbleweed

data class ClassStructure(
  val packageName: String,
  val className: String,
  val fields: List<Field>,
  val methods: List<Method>,
  val relationships: List<Relationship>,
) {
  data class Node(
    val type: Relationship.Type? = null,
    val member: Member,
  ) {
    companion object {
      fun root(member: Member): Node {
        return Node(null, member)
      }
    }
  }

  fun simplify(): ClassStructure {
    val lambdas = relationships
      .filter { it.type == Relationship.Type.Calls }
      .map { it.target as Method }
      .filter { it.isLambda }
      .toSet()

    val bridges = relationships
      .flatMap { listOf(it.source, it.target) }
      .filterIsInstance<Method>()
      .filter(Method::isBridge)
      .toSet()

    val nonSyntheticRelationships = skipLambdasInCallChain(relationships)
      .filter { it.source !in bridges }

    return this.copy(
      methods = methods - lambdas - bridges,
      relationships = nonSyntheticRelationships,
    )
  }

  private fun skipLambdasInCallChain(relationships: List<Relationship>): List<Relationship> {
    val graph = relationships.asGraph()
    val startingPointMethods = graph.keys.filter { !(it as Method).isLambda }.map { it as Method }
    val paths = mutableListOf<List<Node>>()

    val queue = ArrayDeque<List<Node>>()
    for (startMethod in startingPointMethods) {
      queue.add(listOf(Node.root(startMethod)))
      while (queue.isNotEmpty()) {
        val currentPath = queue.removeFirst()
        val destinations = graph[currentPath.last().member]
        val pathNotFound = destinations != null && !isRecursiveCall(currentPath, destinations.last(), 2)
        if (pathNotFound) {
          for (destination in destinations!!) {
            val newPath = currentPath + destination
            if (isRecursiveCall(newPath, destination, 3)) {
              break
            }
            queue.add(newPath)
          }
        } else {
          paths.add(currentPath)
        }
      }
    }

    return paths
      .map(::removeLambdasFromChain)
      .map { path -> path.toRelationships() }
      .flatten()
      .distinct()
  }

  private fun isRecursiveCall(
    path: List<Node>,
    destination: Node,
    nodesToCheck: Int = 3,
  ): Boolean {
    return path
      .takeLast(nodesToCheck)
      .map { it.member.signature }
      .all { it == destination.member.signature } && path.takeLast(nodesToCheck).size == nodesToCheck
  }

  private fun removeLambdasFromChain(destinations: List<Node>): List<Node> {
    return destinations.filter { if (it.member is Method) !it.member.isLambda else true }
  }

  private fun List<Node>.toRelationships(): List<Relationship> {
    return this
      .zipWithNext()
      .map { (source, target) ->
        Relationship(source.member, target.member, target.type!!)
      }
  }

  private fun List<Relationship>.asGraph(): MutableMap<Member, List<Node>> {
    val graph = mutableMapOf<Member, List<Node>>()
    for (relationship in this) {
      val source = relationship.source
      val target = relationship.target
      val type = relationship.type

      val destinations = graph[source] ?: emptyList()
      val newDestinations = destinations + Node(type, target)
      graph[source] = newDestinations
    }
    return graph
  }
}
