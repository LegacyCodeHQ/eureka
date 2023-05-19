package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.ClassStructure
import com.legacycode.tumbleweed.web.observablehq.classifiers.BasicMemberClassifier
import com.legacycode.tumbleweed.web.observablehq.classifiers.MemberClassifier

fun ClassStructure.toGraph(
  classifier: MemberClassifier = BasicMemberClassifier(),
): BilevelEdgeBundlingGraph {
  return Transformer(classifier).transform(this)
}
