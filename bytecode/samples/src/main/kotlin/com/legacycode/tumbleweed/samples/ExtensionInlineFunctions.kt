package com.legacycode.tumbleweed.samples

class ExtensionInlineFunctions {
  fun main() {
    com.legacycode.tumbleweed.samples.listener.ActionConsumer().doIt {
      it.run { bax() }
    }
  }

  private fun bax() {
    println("bax")
  }
}
