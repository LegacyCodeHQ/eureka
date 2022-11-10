@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

class AccessSuperClassMembers : SuperClass() {
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
