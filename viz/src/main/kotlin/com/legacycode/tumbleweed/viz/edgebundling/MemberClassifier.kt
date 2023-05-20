package com.legacycode.tumbleweed.viz.edgebundling

import com.legacycode.tumbleweed.Member

interface MemberClassifier {
  fun groupOf(member: Member): Int
}
