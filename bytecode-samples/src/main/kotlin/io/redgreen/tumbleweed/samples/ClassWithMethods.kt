@file:Suppress("unused", "UNUSED_PARAMETER")

package io.redgreen.tumbleweed.samples

import java.lang.reflect.Member
import java.time.format.DateTimeFormatter
import java.util.Currency

class ClassWithMethods {
  fun methodWithNoArgumentsNoReturnType() { /* empty-body */ }
  fun methodWithNoArgumentsWithReturnType(): Int = 42
  fun methodWithArgumentsWithReturnType(a: String, b: DateTimeFormatter) = "Hello, $a and $b!"
  fun methodWithPrimitiveArgs(a: Int, b: Boolean, c: Double, d: Long) = 12
  fun methodWithArgumentNoReturnType(currency: Currency) { /* empty-body */ }
  fun methodWithGenericReturnType(members: Set<Member>): List<Class<*>> {
    return emptyList()
  }
}
