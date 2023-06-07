package com.legacycode.eureka.samples

class AnonymousFunctionDifferentPackage {
  fun main() {
    com.legacycode.eureka.samples.listener.ActionConsumer().waitForAction {
      celebrate()
    }
  }

  private fun celebrate() {
    println("Hooray!")
  }
}
