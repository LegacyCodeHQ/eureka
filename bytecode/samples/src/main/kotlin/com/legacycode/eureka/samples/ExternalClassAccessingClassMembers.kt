@file:Suppress("SameParameterValue", "unused")

package com.legacycode.eureka.samples

object Logger {
  fun log(severity: Int, tag: String, message: String) {
    println("[$severity] $tag: $message")
  }
}

class ExternalClassAccessingClassMembers {
  private val severity = 5

  private fun whoIs(name: String): String {
    return "I am $name"
  }

  fun foo() {
    Logger.log(severity, "testing", whoIs("foo"))
  }
}
