@file:Suppress("unused")

package com.legacycode.tumbleweed.samples

class AccessSuperClassMembers : com.legacycode.tumbleweed.samples.SuperClass() {
  fun bar() {
    foo()
  }

  fun baz() {
    println(baz)
  }
}

abstract class SuperClass {
  val baz: String = "baz"

  fun foo() {
    println("foo")
  }
}
