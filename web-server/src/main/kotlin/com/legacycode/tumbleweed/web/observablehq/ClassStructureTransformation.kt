package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.ClassStructure

fun ClassStructure.toGraph(): BilevelEdgeBundlingGraph =
  Transformer().transform(this)
