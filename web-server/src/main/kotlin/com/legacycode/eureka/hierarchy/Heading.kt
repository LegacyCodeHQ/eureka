package com.legacycode.eureka.hierarchy

class Heading(
  private val filename: String,
  private val className: String,
  private val pruneKeyword: String?,
) {
  val displayText: String
    get() {
      return if (pruneKeyword != null) {
        "$filename ($className → showing \"$pruneKeyword\")"
      } else {
        "$filename ($className)"
      }
    }
}
