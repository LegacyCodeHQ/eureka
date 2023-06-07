@file:Suppress("MemberVisibilityCanBePrivate")

package com.legacycode.eureka.samples

class MethodsCallingMethods {
  fun a() {
    b()
  }

  fun b() {
    c()
  }

  fun c() {
    d()
  }

  fun d() {
    /* no-op */
  }
}
