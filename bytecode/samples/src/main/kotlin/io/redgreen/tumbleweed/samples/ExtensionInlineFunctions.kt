package io.redgreen.tumbleweed.samples

import io.redgreen.tumbleweed.samples.listener.ActionConsumer

class ExtensionInlineFunctions {
  fun main() {
    ActionConsumer().doIt {
      it.run { bax() }
    }
  }

  private fun bax() {
    println("bax")
  }
}
