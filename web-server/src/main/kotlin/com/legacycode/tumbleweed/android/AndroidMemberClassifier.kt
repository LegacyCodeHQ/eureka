package com.legacycode.tumbleweed.android

import com.legacycode.tumbleweed.Field
import com.legacycode.tumbleweed.FieldSignature
import com.legacycode.tumbleweed.Member
import com.legacycode.tumbleweed.Method
import com.legacycode.tumbleweed.web.observablehq.classifiers.BasicMemberClassifier
import com.legacycode.tumbleweed.web.observablehq.classifiers.MemberClassifier

class AndroidMemberClassifier : MemberClassifier {
  companion object {
    private const val ANDROID_PROPERTY_GROUP = 3
    private const val ANDROID_FRAMEWORK_METHOD_GROUP = 4
  }

  private val memberClassifier = BasicMemberClassifier()

  private val androidPackagePrefixes = listOf(
    "android",
    "androidx",
  ).map { "$it." }

  private val frameworkMethodSignatures = listOf(
    "void onResume()",
    "void onPause()",
  )

  override fun groupOf(member: Member): Int {
    return when (member) {
      is Field -> groupField(member)
      is Method -> groupMethod(member)
      else -> memberClassifier.groupOf(member)
    }
  }

  private fun groupField(field: Field): Int {
    val packageName = (field.signature as FieldSignature).type.packageName
    val isAndroidFrameworkClass = androidPackagePrefixes.any { packageName.startsWith(it) }
    return if (isAndroidFrameworkClass) {
      ANDROID_PROPERTY_GROUP
    } else {
      memberClassifier.groupOf(field)
    }
  }

  private fun groupMethod(method: Method): Int {
    val methodSignature = method.signature.verbose
    val isAndroidFrameworkMethod = methodSignature in frameworkMethodSignatures
    return if (isAndroidFrameworkMethod) {
      ANDROID_FRAMEWORK_METHOD_GROUP
    } else {
      memberClassifier.groupOf(method)
    }
  }
}
