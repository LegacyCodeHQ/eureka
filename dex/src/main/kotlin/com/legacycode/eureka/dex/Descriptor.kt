package com.legacycode.eureka.dex

@JvmInline
value class Descriptor private constructor(val name: String) {
  companion object {
    fun from(name: String): Descriptor {
      return Descriptor(name)
    }
  }

  private val simpleClassName: String
    get() = name.substring(name.lastIndexOf('/') + 1).dropLast(1)

  fun matches(searchTerm: SearchTerm): Boolean {
    return searchTerm.matches(simpleClassName)
  }
}
