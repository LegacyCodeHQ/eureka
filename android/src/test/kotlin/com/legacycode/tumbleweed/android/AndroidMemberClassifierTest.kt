package com.legacycode.tumbleweed.android

import com.legacycode.tumbleweed.android.test.TestClassResource
import com.legacycode.tumbleweed.viz.edgebundling.CompiledClassFile
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

class AndroidMemberClassifierTest {
  @Test
  fun `it can classify Android members`() {
    // given
    val fragmentClassFile = TestClassResource("StoryViewerPageFragment").file

    // when
    val compiledClassFile = CompiledClassFile(fragmentClassFile, AndroidMemberClassifier())

    // then
    JsonApprovals.verifyJson(compiledClassFile.graph.toJson())
  }
}
