@file:Suppress("unused")

package com.legacycode.tumbleweed.samples

class StaticFieldAccess {
  fun turnDebugOn() {
    com.legacycode.tumbleweed.samples.DeclareMutableStaticFields.DEBUG = true
  }

  fun turnDebugOff() {
    com.legacycode.tumbleweed.samples.DeclareMutableStaticFields.DEBUG = false
  }
}
