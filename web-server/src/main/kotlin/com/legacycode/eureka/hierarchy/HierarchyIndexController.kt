package com.legacycode.eureka.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.InheritanceAdjacencyList
import com.legacycode.eureka.dex.TreeClusterJsonTreeBuilder
import java.io.File

class HierarchyIndexController(
  private val artifactFile: File,
  private val adjacencyList: InheritanceAdjacencyList,
  private val defaultAncestor: Ancestor,
) {
  suspend fun handleRequest(effects: HierarchyIndexPathEffects) {
    val className = effects.getClassParameter()
    val urlHasClassParameter = className != null

    if (!urlHasClassParameter) {
      val currentUrl = effects.getCurrentUrl()
      val redirectUrl = "$currentUrl?class=${defaultAncestor.fqn}"
      effects.redirectRequestTo(redirectUrl)
    } else {
      val root = Ancestor(toClassDescriptor(className!!))
      val searchTerm = effects.getPruneParameter()
      val activeAdjacencyList = if (searchTerm != null) {
        adjacencyList.prune(searchTerm)
      } else {
        adjacencyList
      }

      val html = HierarchyHtml(
        Title(artifactFile.name, root.fqn),
        Heading(artifactFile.name, root.fqn, searchTerm),
        activeAdjacencyList.tree(root, TreeClusterJsonTreeBuilder()),
      ).content
      effects.renderHtml(html)
    }
  }

  private fun toClassDescriptor(className: String): String {
    return "L${className.replace('.', '/')};"
  }
}
