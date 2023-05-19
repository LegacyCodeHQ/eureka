package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.Field
import com.legacycode.tumbleweed.Member
import com.legacycode.tumbleweed.Method

class BasicMemberClassifier : MemberClassifier {
  override fun groupOf(member: Member): Int {
    return when (member) {
      is Field -> 1
      is Method -> 2
    }
  }
}
