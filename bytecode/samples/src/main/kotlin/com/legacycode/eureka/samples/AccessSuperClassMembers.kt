@file:Suppress("unused")

package com.legacycode.eureka.samples

class AccessSuperClassMembers : com.legacycode.eureka.samples.SuperClass() {
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
