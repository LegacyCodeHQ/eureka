@file:Suppress("unused", "RedundantLambdaOrAnonymousFunction")

package com.legacycode.tumbleweed.samples

class NestedAnonymousFunctionWritingField {
  var counter: Int = 0

  fun incrementCounter() {
    { { { { counter++ }() }() }() }()
  }
}
