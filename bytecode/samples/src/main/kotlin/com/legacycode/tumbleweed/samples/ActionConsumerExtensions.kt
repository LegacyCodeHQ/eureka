package com.legacycode.tumbleweed.samples

inline fun com.legacycode.tumbleweed.samples.listener.ActionConsumer.doIt(crossinline block: (Any) -> Unit) {
  block(this)
}
