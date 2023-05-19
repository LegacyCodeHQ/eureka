package com.legacycode.tumbleweed.web.observablehq.classifiers

import com.legacycode.tumbleweed.test.TestClassResource
import com.legacycode.tumbleweed.web.CompiledClassFile
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
