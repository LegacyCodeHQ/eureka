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
      .containsExactly(Ancestor("Ljava/lang/Object;"))
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

  @Test
  fun `returns true if a node is present (ancestor)`() {
    // given
    with(adjacencyList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node;"))
    }

    // when
    val containsNode = adjacencyList.contains("Ljava/lang/Object;")

    // then
    assertThat(containsNode)
      .isTrue()
  }

  @Test
  fun `returns true if a node is present (child)`() {
    // given
    with(adjacencyList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node;"))
    }

    // when
    val containsNode = adjacencyList.contains("Lcom/legacycode/dex/Node;")

    // then
    assertThat(containsNode)
      .isTrue()
  }

  @Test
  fun `returns false if a node is absent`() {
    // given
    with(adjacencyList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child;"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node;"))
    }

    // when
    val containsNode = adjacencyList.contains("Lcom/legacycode/dex/Node;")

    // then
    assertThat(containsNode)
      .isTrue()
  }
}
