package io.redgreen.tumbleweed.samples

import io.redgreen.tumbleweed.samples.listener.ActionConsumer

inline fun ActionConsumer.doIt(crossinline block: (Any) -> Unit) {
  block(this)
}
