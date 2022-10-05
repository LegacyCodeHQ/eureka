package io.redgreen.tumbleweed

typealias Index = Int

@JvmInline
value class MethodDescriptor(private val descriptor: String) {
  data class ClassTokenRange(val start: Index, val end: Index) {
    fun inRange(index: Index): Boolean =
      index in start..end
  }

  val returnType: String
    get() {
      val typeDescriptor = if (hasNoParameters) {
        descriptor.replace("()", "")
      } else {
        descriptor.substring(descriptor.indexOf(")") + 1)
      }

      return TypeToken(typeDescriptor).type
    }

  val parameters: List<String>
    get() {
      if (hasNoParameters) {
        return emptyList()
      }

      val parametersDescriptor = descriptor.substring(descriptor.indexOf("(") + 1, descriptor.indexOf(")"))
      val objectTypeTokenRanges = objectTypeTokenRanges(parametersDescriptor)
      val typeTokens = mutableListOf<TypeToken>()
      val possiblyPrimitiveTokens = parametersDescriptor.split("").filter { it.isNotBlank() }

      var index = 0
      while (index < possiblyPrimitiveTokens.size) {
        if (!objectTypeTokenRanges.any { it.inRange(index) }) {
          typeTokens.add(TypeToken(possiblyPrimitiveTokens[index]))
          index++
        } else {
          val objectTypeTokenRange = objectTypeTokenRanges.first { it.inRange(index) }
          val objectTypeToken = parametersDescriptor.substring(objectTypeTokenRange.start, objectTypeTokenRange.end + 1)
          typeTokens.add(TypeToken(objectTypeToken))
          index = objectTypeTokenRange.end + 1
        }
      }

      return typeTokens.map(TypeToken::type)
    }

  private fun objectTypeTokenRanges(parametersDescriptor: String): List<ClassTokenRange> {
    val startIndicesOfClassTokens = parametersDescriptor.foldIndexed(mutableListOf<Index>()) { index, acc, char ->
      if (char == 'L') acc.add(index)
      acc
    }

    val endIndicesOfTokens = parametersDescriptor.foldIndexed(mutableListOf<Index>()) { index, acc, char ->
      if (char == ';') acc.add(index)
      acc
    }

    return startIndicesOfClassTokens
      .zip(endIndicesOfTokens)
      .map { (start, end) ->
        ClassTokenRange(start, end)
      }
  }

  private val hasNoParameters: Boolean
    get() = descriptor.contains("()")
}
