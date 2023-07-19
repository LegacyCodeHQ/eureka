package com.legacycode.eureka.dex.inheritance

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child
import com.legacycode.eureka.dex.test.TestApk
import com.legacycode.eureka.dex.test.TestTreeBuilder
import org.approvaltests.Approvals
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InheritanceTreeBuilderTest {
  private val adjacencyList = AdjacencyList()

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
  fun `it can build JSON document for Tree Cluster`() {
    // given
    val treeClusterJsonBuilder = TreeClusterJsonTreeBuilder()

    // when
    val treeClusterJson = adjacencyList.tree(Ancestor("Landroid/app/Activity;"), treeClusterJsonBuilder)

    // then
    JsonApprovals.verifyJson(treeClusterJson)
  }

  @Test
  fun `it can prune a tree by specifying a keyword`() {
    // given
    val treeClusterJsonBuilder = TreeClusterJsonTreeBuilder()

    // when
    val prunedAdjacencyList = adjacencyList.prune("Home")

    // then
    val treeClusterJson = prunedAdjacencyList.tree(Ancestor("Landroid/app/Activity;"), treeClusterJsonBuilder)
    JsonApprovals.verifyJson(treeClusterJson)
  }

  @Test
  fun `it can prune a tree by specifying a keyword (ignore case)`() {
    // given
    val treeClusterJsonBuilder = TreeClusterJsonTreeBuilder()

    // when
    val prunedAdjacencyList = adjacencyList.prune("home")

    // then
    val treeClusterJson = prunedAdjacencyList.tree(Ancestor("Landroid/app/Activity;"), treeClusterJsonBuilder)
    JsonApprovals.verifyJson(treeClusterJson)
  }

  @Test
  fun `it can prune a tree by specifying a keyword and ignore non-matching siblings`() {
    // given
    val apkFile = TestApk("wikipedia.apk").file
    val signalAdjacencyList = InheritanceArtifactParser.from(apkFile).buildInheritanceTree()

    // when
    val prunedAdjacencyList = signalAdjacencyList.prune("Settings")

    // then
    val treeClusterJson = prunedAdjacencyList
      .tree(Ancestor("Landroid/app/Activity;"), TreeClusterJsonTreeBuilder())
    JsonApprovals.verifyJson(treeClusterJson)
  }
}
