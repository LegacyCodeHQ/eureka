@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

import java.time.format.DateTimeFormatter

class ClassWithMethods {
  fun methodWithNoArgumentsNoReturnType() { /* empty-body */ }
  fun methodWithNoArgumentsWithReturnType(): Int = 42
  fun methodWithArgumentsWithReturnType(a: String, b: DateTimeFormatter) = "Hello, $a and $b!"
}
