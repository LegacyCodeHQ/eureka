@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

class FunctionCallsInsideLambdas {
  fun foo() {
    Observable()
      .onChange { bar() }
  }

  private fun bar() {
    val list = listOf(1, 2, 3)
    list.forEach { println(it) }
  }
}

class Observable {
  fun onChange(callback: () -> Unit) {
    callback()
  }
}
