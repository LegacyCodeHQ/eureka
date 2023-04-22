@file:Suppress("NOTHING_TO_INLINE", "unused", "SameParameterValue")

package com.legacycode.tumbleweed.samples

class InlineFunction {
  private val logger: StringBuilder = StringBuilder()

  fun sum() {
    println(add(1, 2) { logger.appendLine(this) })
  }
}

private inline fun add(
  a: Int,
  b: Int,
  crossinline block: Int.() -> Unit,
): Int {
  val sum = a + b
  sum.block()
  return sum
}
