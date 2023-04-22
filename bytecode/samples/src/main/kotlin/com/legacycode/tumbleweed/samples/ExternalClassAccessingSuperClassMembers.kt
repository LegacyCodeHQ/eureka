@file:Suppress("SameParameterValue", "unused")

package com.legacycode.tumbleweed.samples

class ExternalClassAccessingSuperClassMembers : InternalSuperClass() {
  fun foo() {
    Logger.log(severity, "testing", whoIs("foo"))
  }
}

abstract class InternalSuperClass {
  protected val severity = 5

  protected fun whoIs(name: String): String {
    return "I am $name"
  }
}
