package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.Member

interface MemberClassifier {
  fun groupOf(member: Member): Int
}
