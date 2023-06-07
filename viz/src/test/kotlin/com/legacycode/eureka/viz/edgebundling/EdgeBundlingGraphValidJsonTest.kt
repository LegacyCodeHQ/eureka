package com.legacycode.eureka.viz.edgebundling

import com.google.common.truth.Truth.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

internal class EdgeBundlingGraphValidJsonTest {
  @Test
  internal fun `valid edge bundling graph json`() {
    // given
    @Language("json")
    val jsonString = """
      {
        "nodes": [
          {
            "id": "String name",
            "group": 1
          },
          {
            "id": "void \u003cinit\u003e()",
            "group": 2
          },
          {
            "id": "String getName()",
            "group": 2
          }
        ],
        "links": [
          {
            "source": "String getName()",
            "target": "String name",
            "value": 1
          }
        ]
      }
    """

    // when
    val isJsonValid = EdgeBundlingGraph.isValidJson(jsonString)

    // then
    assertThat(isJsonValid)
      .isTrue()
  }

  @Test
  internal fun `invalid edge bundling graph json`() {
    // given
    @Language("json")
    val jsonString = """
      {
        "nodes": [
          {
            "id": "String name",
            "group": 1
          },
          {
            "id": "void \u003cinit\u003e()",
            "group": 2
          },
          {
            "id": "String getName()",
            "group": 2
          }
        ]
      }
    """

    // when
    val isJsonValid = EdgeBundlingGraph.isValidJson(jsonString)

    // then
    assertThat(isJsonValid)
      .isFalse()
  }
}
