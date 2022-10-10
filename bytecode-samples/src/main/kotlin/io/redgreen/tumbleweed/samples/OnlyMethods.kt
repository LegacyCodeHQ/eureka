@file:Suppress("unused", "UNUSED_PARAMETER")

package io.redgreen.tumbleweed.samples

import java.lang.reflect.Member
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Date

class OnlyMethods {
  fun methodWithNoArgumentsNoReturnType() { /* empty-body */ }
  fun methodWithNoArgumentsWithReturnType(): Int = 42
  fun methodWithArgumentsWithReturnType(a: String, b: DateTimeFormatter) = "Hello, $a and $b!"
  fun methodWithPrimitiveArgs(a: Int, b: Boolean, c: Double, d: Long) = 12
  fun methodWithArgumentNoReturnType(currency: Currency) { /* empty-body */ }
  fun methodWithGenericReturnType(members: Set<Member>): List<Class<*>> {
    return emptyList()
  }
  fun methodWithPrimitiveArrayArgumentNoReturnType(indices: Array<Int>) { /* empty-body */ }
  fun methodWithNoArgumentsObjectArrayReturnType(): Array<Date> {
    return emptyArray()
  }
  fun methodWithNoArgumentsPrimitiveArrayReturnType(): IntArray {
    return intArrayOf(1, 2, 3)
  }
}
