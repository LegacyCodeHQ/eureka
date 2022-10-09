package io.redgreen.tumbleweed

data class ClassStructure(
  val `package`: String,
  val className: String,
  val fields: List<Field>,
  val methods: List<Method>,
  val relationships: List<Relationship>,
) {
  fun concise(): ClassStructure {
    val lambdaToDeclaringFunctionMap = relationships
      .filter { it.source is Method && it.target is Method }
      .filter { (it.target as Method).name.contains("\$lambda-") }
      .associate { it.target to it.source }

    val lambdaFunctions = relationships
      .filter { it.type == Relationship.Type.Calls }
      .map { it.target as Method }
      .filter { it.name.contains("\$lambda-") }
      .toSet()

    val relationshipsWithoutLambdas = relationships
      .map { relationship ->
        if (relationship.source in lambdaToDeclaringFunctionMap.keys) {
          relationship.copy(source = lambdaToDeclaringFunctionMap[relationship.source]!!)
        } else {
          relationship
        }
      }
      .filter {
        !(it.target is Method && it.target.name.contains("\$lambda-"))
      }

    return this.copy(
      methods = methods - lambdaFunctions,
      relationships = relationshipsWithoutLambdas,
    )
  }
}
