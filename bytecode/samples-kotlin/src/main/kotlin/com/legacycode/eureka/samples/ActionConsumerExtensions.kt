package com.legacycode.eureka.samples

inline fun com.legacycode.eureka.samples.listener.ActionConsumer.doIt(crossinline block: (Any) -> Unit) {
  block(this)
}
