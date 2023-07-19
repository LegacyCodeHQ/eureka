package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class AdjacencyListTest {
  private val adjacencyList = AdjacencyList()

  @Test
  fun `create an empty inheritance adjacency list`() {
    assertThat(adjacencyList.isEmpty)
      .isTrue()
  }

  @Test
  fun `a registry with at least one entry is non-empty`() {
    // when
    adjacencyList.add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))

    // then
    assertThat(adjacencyList.isEmpty)
      .isFalse()
  }

  @Test
  fun `get ancestor`() {
    // when
    adjacencyList.add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))

    // then
    assertThat(adjacencyList.ancestors())
      .containsExactly("Ljava/lang/Object;")
  }

  @Test
  fun `get children`() {
    // when
    with(adjacencyList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node;"))
    }

    // then
    assertThat(adjacencyList.children(Ancestor("Ljava/lang/Object;")))
      .containsExactly(
        Child("Lcom/legacycode/dex/Child;"),
        Child("Lcom/legacycode/dex/Node;"),
      )
  }

  @Test
  fun `return an empty set of children when parent does not exist`() {
    // when
    with(adjacencyList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node;"))
    }

    // then
    assertThat(adjacencyList.children(Ancestor("Landroid/app/Activity;")))
      .isEmpty()
  }
}
