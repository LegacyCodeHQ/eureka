package com.legacycode.eureka.dex

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ApkParserTest {
  @Test
  fun `it can build an inheritance register from an APK`() {
    // given
    val apkFile = TestApk("wikipedia.apk").file
    val parser = ApkParser(apkFile)
    val register = parser.inheritanceRegister()

    // when
    val graphvizTree = register.tree(
      Ancestor("Landroid/app/Activity;"),
      DotTreeBuilder("Wikipedia Activities"),
    )

    // then
    Approvals.verify(graphvizTree)
  }
}
