package io.redgreen.tumbleweed.web.observablehq

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class BilevelEdgeBundlingGraphValidJsonTest {
  @Test
  internal fun `valid edge bundling graph json`() {
    // given
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
    val isJsonValid = BilevelEdgeBundlingGraph.isValidJson(jsonString)

    // then
    assertEquals(isJsonValid, true)
  }

  @Test
  internal fun `invalid edge bundling graph json`() {
    // given
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
    val isJsonValid = BilevelEdgeBundlingGraph.isValidJson(jsonString)

    // then
    assertEquals(isJsonValid, false)
  }
}
