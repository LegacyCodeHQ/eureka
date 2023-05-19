package com.legacycode.tumbleweed.web.observablehq.classifiers

import com.legacycode.tumbleweed.Field
import com.legacycode.tumbleweed.FieldSignature
import com.legacycode.tumbleweed.Member

class AndroidMemberClassifier : MemberClassifier {
  private val memberClassifier = BasicMemberClassifier()

  private val androidPackagePrefixes = listOf(
    "android",
    "androidx",
  ).map { "$it." }

  override fun groupOf(member: Member): Int {
    if (member is Field) {
      val packageName = (member.signature as FieldSignature).type.packageName
      if (androidPackagePrefixes.any { packageName.startsWith(it) }) {
        return 3
      }
    }
    return memberClassifier.groupOf(member)
  }
}
