package io.redgreen.tumbleweed

@JvmInline
value class MethodDescriptor(private val descriptor: String) {
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

      return descriptor
        .substring(descriptor.indexOf("(") + 1, descriptor.indexOf(")"))
        .split(";")
        .filter { it.isNotBlank() }
        .map { TypeToken(it).type }
    }

  private val hasNoParameters: Boolean
    get() = descriptor.contains("()")
}
