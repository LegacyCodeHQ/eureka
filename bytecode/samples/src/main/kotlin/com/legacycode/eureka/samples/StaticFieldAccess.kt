@file:Suppress("unused")

package com.legacycode.eureka.samples

class StaticFieldAccess {
  fun turnDebugOn() {
    com.legacycode.eureka.samples.DeclareMutableStaticFields.DEBUG = true
  }

  fun turnDebugOff() {
    com.legacycode.eureka.samples.DeclareMutableStaticFields.DEBUG = false
  }
}
