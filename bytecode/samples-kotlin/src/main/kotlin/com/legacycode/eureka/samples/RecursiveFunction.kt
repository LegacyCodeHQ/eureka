@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.legacycode.eureka.samples

object RecursiveFunction {
  fun fibonacci(n: Int): Int {
    if (n <= 1) {
      return n
    }
    return fibonacci(n - 1) + fibonacci(n - 2)
  }
}
