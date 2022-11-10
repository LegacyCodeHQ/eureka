@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

class StaticFieldAccess {
  fun turnDebugOn() {
    DeclareMutableStaticFields.DEBUG = true
  }

  fun turnDebugOff() {
    DeclareMutableStaticFields.DEBUG = false
  }
}
