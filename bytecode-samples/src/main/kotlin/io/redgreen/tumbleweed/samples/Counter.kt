@file:Suppress("unused")

package io.redgreen.tumbleweed.samples

data class Counter(val count: Int = 0) {
  fun increment(): Counter = copy(count = count + 1)
}
