package com.legacycode.eureka.samples

class ExtensionInlineFunctions {
  fun main() {
    com.legacycode.eureka.samples.listener.ActionConsumer().doIt {
      it.run { bax() }
    }
  }

  private fun bax() {
    println("bax")
  }
}
