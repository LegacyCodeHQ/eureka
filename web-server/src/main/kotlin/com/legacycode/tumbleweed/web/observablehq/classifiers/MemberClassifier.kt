package com.legacycode.tumbleweed.web.observablehq.classifiers

import com.legacycode.tumbleweed.Member

interface MemberClassifier {
  fun groupOf(member: Member): Int
}
