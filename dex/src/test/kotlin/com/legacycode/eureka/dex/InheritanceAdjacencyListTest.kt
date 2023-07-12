package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.approvaltests.Approvals
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InheritanceAdjacencyListTest {
  private val adjacencyList = InheritanceAdjacencyList()

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

  @Nested
  inner class Tree {
    @BeforeEach
    fun beforeEach() {
      with(adjacencyList) {
        add(Ancestor("Landroid/app/Activity;"), Child("Landroidx/app/AppCompatActivity;"))
        add(Ancestor("Landroidx/app/AppCompatActivity;"), Child("Lcom/legacycode/app/BaseActivity;"))
        add(Ancestor("Lcom/legacycode/app/BaseActivity;"), Child("Landroid/app/HomeActivity;"))
        add(Ancestor("Landroidx/app/AppCompatActivity;"), Child("Lcom/legacycode/MetricsActivity;"))
        add(Ancestor("Landroid/app/Fragment;"), Child("Landroidx/app/AppCompatFragment;"))
      }
    }

    @Test
    fun `it can build a tree`() {
      // given
      val treeBuilder = TestTreeBuilder()

      // when
      val tree = adjacencyList.tree(Ancestor("Landroid/app/Activity;"), treeBuilder)

      // then
      Approvals.verify(tree)
    }

    @Test
    fun `it can build a graphviz tree`() {
      // given
      val dotTreeBuilder = DotTreeBuilder("Activity")

      // when
      val graphvizTree = adjacencyList.tree(Ancestor("Landroid/app/Activity;"), dotTreeBuilder)

      // then
      Approvals.verify(graphvizTree)
    }

    @Test
    fun `it can build JSON document for Tree, Cluster`() {
      // given
      val treeClusterJsonBuilder = TreeClusterJsonTreeBuilder()

      // when
      val treeClusterJson = adjacencyList.tree(Ancestor("Landroid/app/Activity;"), treeClusterJsonBuilder)

      // then
      JsonApprovals.verifyJson(treeClusterJson)
    }
  }
}
