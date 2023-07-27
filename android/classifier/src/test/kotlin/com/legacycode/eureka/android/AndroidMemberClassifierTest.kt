package com.legacycode.eureka.android

import com.legacycode.eureka.testing.TestClassResource
import com.legacycode.eureka.viz.edgebundling.CompiledClassFile
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
