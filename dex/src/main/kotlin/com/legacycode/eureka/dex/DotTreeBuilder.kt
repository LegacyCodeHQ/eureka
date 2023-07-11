package com.legacycode.eureka.dex

class DotTreeBuilder(private val title: String) : InheritanceRegister.TreeBuilder<String> {
  private val stringBuilder = StringBuilder()
  private lateinit var ancestor: Ancestor

  override val out: String
    get() = stringBuilder.toString()

  override fun beforeTraversal() {
    stringBuilder.appendLine("digraph \"$title\" {")
  }

  override fun visitAncestor(ancestor: Ancestor) {
    this.ancestor = ancestor
  }

  override fun visitChild(child: Child) {
    stringBuilder.appendLine("  \"${child.fqn}\" -> \"${ancestor.fqn}\"")
  }

  private val Node.fqn: String
    get() {
      return id.replace('/', '.').drop(1).dropLast(1)
    }

  override fun afterTraversal() {
    stringBuilder.appendLine("}")
  }
}
