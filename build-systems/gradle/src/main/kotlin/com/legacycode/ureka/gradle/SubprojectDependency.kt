package com.legacycode.ureka.gradle

@JvmInline
value class SubprojectDependency(val name: String) {
  companion object {
    private val regex = "^\\+--- project\\s+:?([\\w_-]+)".toRegex()

    fun from(line: String): SubprojectDependency? {
      val matchResult = regex.find(line)
      val name = matchResult?.groupValues?.get(1)
      if (name != null) {
        return SubprojectDependency(name)
      }
      return null
    }
  }
}
