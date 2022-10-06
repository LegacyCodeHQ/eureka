@file:Suppress("MemberVisibilityCanBePrivate")

package io.redgreen.tumbleweed.samples

class ClassWithMethodsCallingMethods {
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
