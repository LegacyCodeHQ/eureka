package io.redgreen.tumbleweed.samples

class InterfaceImplementation : CounterActions {
  var count = 0

  override fun increment() {
    count++
  }

  override fun decrement() {
    count--
  }
}

interface CounterActions {
  fun increment()
  fun decrement()
}
