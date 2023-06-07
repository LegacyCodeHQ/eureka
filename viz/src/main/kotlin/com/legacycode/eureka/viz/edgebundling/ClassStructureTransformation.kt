package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.ClassStructure

fun ClassStructure.toGraph(
  classifier: MemberClassifier = BasicMemberClassifier(),
): EdgeBundlingGraph {
  return Transformer(classifier).transform(this)
}
