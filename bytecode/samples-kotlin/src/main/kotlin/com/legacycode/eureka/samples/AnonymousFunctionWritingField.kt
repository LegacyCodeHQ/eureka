@file:Suppress("unused", "RedundantLambdaOrAnonymousFunction")

package com.legacycode.eureka.samples

class AnonymousFunctionWritingField {
  var counter: Int = 0

  fun incrementCounter() {
    { counter++ }.invoke()
  }
}
