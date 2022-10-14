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
      val nonPrimitiveTypeTokenRanges = nonPrimitiveTypeTokenRanges(parametersDescriptor)
      val typeTokens = mutableListOf<TypeToken>()
      val possiblyPrimitiveTokens = parametersDescriptor.split("").filter { it.isNotBlank() }

      var index = 0
      var isArray = false
      while (index < possiblyPrimitiveTokens.size) {
        val tokenChar = possiblyPrimitiveTokens[index]

        if (TypeToken.isPrimitive(tokenChar)) {
          if (isArray) {
            typeTokens.add(TypeToken("[$tokenChar"))
            isArray = false
          } else {
            typeTokens.add(TypeToken(tokenChar))
          }
          index++
        } else if (TypeToken.isObject(tokenChar)) {
          val range = nonPrimitiveTypeTokenRanges.first { it.inRange(index) }
          val typeToken = TypeToken(parametersDescriptor.substring(range.start, range.end + 1))
          if (isArray) {
            typeTokens.add(typeToken)
            isArray = false
          } else {
            typeTokens.add(typeToken)
          }

          index = range.end + 1
        } else if (TypeToken.isArray(tokenChar)) {
          isArray = true
          index++
        } else {
          throw IllegalArgumentException("Unknown type: $tokenChar")
        }
      }

      return typeTokens.map(TypeToken::type)
    }

  private fun nonPrimitiveTypeTokenRanges(parametersDescriptor: String): List<ClassTokenRange> {
    val startIndicesOfNonPrimitiveTypeTokens =
      parametersDescriptor.foldIndexed(mutableListOf<Index>()) { index, acc, char ->
        if (char == 'L' || char == '[') acc.add(index)
        acc
      }

    val endIndicesOfTokens = parametersDescriptor.foldIndexed(mutableListOf<Index>()) { index, acc, char ->
      if (char == ';') acc.add(index)
      acc
    }

    return startIndicesOfNonPrimitiveTypeTokens
      .zip(endIndicesOfTokens)
      .map { (start, end) ->
        ClassTokenRange(start, end)
      }
  }

  private val hasNoParameters: Boolean
    get() = descriptor.contains("()")
}
