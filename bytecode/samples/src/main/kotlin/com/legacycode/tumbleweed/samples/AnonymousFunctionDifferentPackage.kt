package com.legacycode.tumbleweed.samples

class AnonymousFunctionDifferentPackage {
  fun main() {
    com.legacycode.tumbleweed.samples.listener.ActionConsumer().waitForAction {
      celebrate()
    }
  }

  private fun celebrate() {
    println("Hooray!")
  }
}
