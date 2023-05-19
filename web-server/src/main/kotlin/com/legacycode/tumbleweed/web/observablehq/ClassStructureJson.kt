package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.ClassStructure

val ClassStructure.graph: BilevelEdgeBundlingGraph
  get() = Transformer().transform(this)
