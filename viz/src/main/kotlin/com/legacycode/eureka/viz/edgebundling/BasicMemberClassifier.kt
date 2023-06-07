package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.Field
import com.legacycode.eureka.Member
import com.legacycode.eureka.Method

class BasicMemberClassifier : MemberClassifier {
  override fun groupOf(member: Member): Int {
    return when (member) {
      is Field -> 1
      is Method -> 2
    }
  }
}
