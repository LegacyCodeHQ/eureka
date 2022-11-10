package io.redgreen.tumbleweed.samples

import io.redgreen.tumbleweed.samples.listener.ActionConsumer

class AnonymousFunctionDifferentPackage {
  fun main() {
    ActionConsumer().waitForAction {
      celebrate()
    }
  }

  private fun celebrate() {
    println("Hooray!")
  }
}
