@file:Suppress("unused")

package com.legacycode.eureka.samples

class StaticFieldAccess {
  fun turnDebugOn() {
    DeclareMutableStaticFields.DEBUG = true
  }

  fun turnDebugOff() {
    DeclareMutableStaticFields.DEBUG = false
  }
}
