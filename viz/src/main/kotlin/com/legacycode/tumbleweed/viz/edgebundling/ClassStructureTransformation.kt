package com.legacycode.tumbleweed.viz.edgebundling

import com.legacycode.tumbleweed.ClassStructure

fun ClassStructure.toGraph(
  classifier: MemberClassifier = BasicMemberClassifier(),
): EdgeBundlingGraph {
  return Transformer(classifier).transform(this)
}
