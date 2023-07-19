package com.legacycode.eureka.dex.inheritance

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.test.TestApk
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class InheritanceApkParserTest {
  private val apkFile = TestApk("wikipedia.apk").file

  @Test
  fun `it can build an inheritance adjacency list from an APK`() {
    // given
    val parser = InheritanceArtifactParser.from(apkFile)
    val adjacencyList = parser.buildInheritanceTree()

    // when
    val graphvizTree = adjacencyList.tree(
      Ancestor("Landroid/app/Activity;"),
      DotTreeBuilder("Wikipedia Activities"),
    )

    // then
    Approvals.verify(graphvizTree)
  }

  @Test
  fun `it should ignore anonymous inner classes`() {
    // given
    val adjacencyList = InheritanceArtifactParser.from(apkFile)
      .buildInheritanceTree()

    // when
    val graphvizTree = adjacencyList.tree(
      Ancestor("Ljava/lang/Object;"),
      DotTreeBuilder("Everything, all at once!"),
    )

    // then
    Approvals.verify(graphvizTree)
  }
}
