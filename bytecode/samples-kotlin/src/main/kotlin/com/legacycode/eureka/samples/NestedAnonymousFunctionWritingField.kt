@file:Suppress("unused", "RedundantLambdaOrAnonymousFunction")

package com.legacycode.eureka.samples

class NestedAnonymousFunctionWritingField {
  var counter: Int = 0

  fun incrementCounter() {
    { { { { counter++ }() }() }() }()
  }
}
