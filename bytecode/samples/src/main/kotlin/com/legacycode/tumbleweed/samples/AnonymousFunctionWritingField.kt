@file:Suppress("unused", "RedundantLambdaOrAnonymousFunction")

package com.legacycode.tumbleweed.samples

class AnonymousFunctionWritingField {
  var counter: Int = 0

  fun incrementCounter() {
    { counter++ }.invoke()
  }
}
