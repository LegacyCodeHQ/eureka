package com.legacycode.eureka.web.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.inheritance.TreeClusterJsonTreeBuilder

class HierarchyIndexController(
  private val artifactFilename: String,
  private val adjacencyList: AdjacencyList,
  private val defaultRootFqn: String,
) {
  suspend fun handleRequest(effects: HierarchyIndexPathEffects) {
    val className = effects.getClassParameter()
    val urlHasClassParameter = className != null

    if (!urlHasClassParameter) {
      val currentUrl = effects.getCurrentUrl()
      val redirectUrl = "$currentUrl?class=$defaultRootFqn"
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
        Title(artifactFilename, root.fqn),
        Heading(artifactFilename, root.fqn, searchTerm),
        activeAdjacencyList.tree(root, TreeClusterJsonTreeBuilder()),
      ).content
      effects.renderHtml(html)
    }
  }

  private fun toClassDescriptor(className: String): String {
    return "L${className.replace('.', '/')};"
  }
}
