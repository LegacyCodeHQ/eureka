@file:Suppress("unused", "RedundantLambdaOrAnonymousFunction")

package io.redgreen.tumbleweed.samples

class NestedAnonymousFunctionWritingField {
  var counter: Int = 0

  fun incrementCounter() {
    { { { { counter++ }() }() }() }()
  }
}
