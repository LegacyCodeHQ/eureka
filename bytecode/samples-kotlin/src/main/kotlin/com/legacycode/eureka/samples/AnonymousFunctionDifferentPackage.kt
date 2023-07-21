package com.legacycode.eureka.samples

import com.legacycode.eureka.samples.listener.ActionConsumer

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
