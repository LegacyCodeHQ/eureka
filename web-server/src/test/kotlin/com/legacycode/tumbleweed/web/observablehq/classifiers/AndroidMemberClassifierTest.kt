package com.legacycode.tumbleweed.web.observablehq.classifiers

import com.legacycode.tumbleweed.web.CompiledClassFile
import java.io.File
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

class AndroidMemberClassifierTest {
  @Test
  fun `it can classify Android members`() {
    // given
    val androidFragmentClassFile = File("./src/test/resources/class-files/StoryViewerPageFragment.class")

    // when
    val compiledClassFile = CompiledClassFile(androidFragmentClassFile, AndroidMemberClassifier())

    // then
    JsonApprovals.verifyJson(compiledClassFile.graph.toJson())
  }
}
