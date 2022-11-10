@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

class SyntheticBridges {
  private var field = "field"

  inner class Something {
    fun method() {
      field = "field"
    }
  }
}
