@file:Suppress("unused")

package com.legacycode.tumbleweed.samples

class SyntheticBridges {
  private var field = "field"

  inner class Something {
    fun method() {
      field = "field"
    }
  }
}
