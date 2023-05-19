package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.Member

interface MemberClassifier {
  fun groupOf(member: Member): Int
}
