package com.legacycode.eureka.dex

@JvmInline
value class Descriptor private constructor(val name: String) {
  companion object {
    fun from(name: String): Descriptor {
      return Descriptor(name)
    }
  }

  fun matches(keyword: String): Boolean {
    val simpleClassName = name.substring(name.lastIndexOf('/') + 1).dropLast(1)
    val isInnerClass = simpleClassName.contains('$')
    if (isInnerClass) {
      val nestedSimpleClassName = simpleClassName.substring(simpleClassName.lastIndexOf('$') + 1)
      return nestedSimpleClassName.contains(keyword, true)
    }

    return simpleClassName.contains(keyword, true)
  }
}
