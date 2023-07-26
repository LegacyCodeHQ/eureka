package com.legacycode.eureka.web.watch

import com.google.common.truth.Truth.assertThat
import kotlin.random.Random.Default.nextInt
import org.junit.jupiter.api.Test

class IndexPageTest {
  @Test
  fun `replace port number in an index HTML file`() {
    // given
    val aPort = nextInt(1000, 10_000)
    val indexPage = IndexPage.newInstance(aPort)

    // when
    val content = indexPage.content

    // then
    assertThat(content)
      .contains("localhost:$aPort")
  }

  @Test
  fun `enable an experiment`() {
    // given
    val activeExperiment = Experiment.android
    val indexPage = IndexPage.newInstance(7070, activeExperiment)

    // when
    val content = indexPage.content

    // then
    assertThat(content)
      .contains("data-experiment=\"${activeExperiment.name}\"")
  }
}
