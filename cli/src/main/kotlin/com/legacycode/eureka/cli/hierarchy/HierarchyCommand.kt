package com.legacycode.eureka.cli.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.ApkParser
import com.legacycode.eureka.dex.DotTreeBuilder
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "hierarchy",
  description = ["prints the inheritance hierarchy of the specified class"],
)
class HierarchyCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to the APK file"],
    arity = "1",
  )
  private lateinit var apkFile: File

  @Option(
    names = ["-n", "--name"],
    description = ["fully qualified class name of the root class"],
    required = true,
  )
  private lateinit var rootClassName: String

  override fun run() {
    val adjacencyList = ApkParser(apkFile).inheritanceAdjacencyList()
    val rootClassDescriptor = toClassDescriptor(rootClassName)
    val title = getTitle(apkFile, rootClassName)
    val root = Ancestor(rootClassDescriptor)

    if (adjacencyList.children(root).isNotEmpty()) {
      val tree = adjacencyList.tree(root, DotTreeBuilder(title))

      println(tree)
      println("Copy and paste the output at https://dreampuf.github.io/GraphvizOnline")
    } else {
      println("Oops… '$rootClassName' does not have an inheritance hierarchy")
    }
  }

  private fun toClassDescriptor(className: String): String {
    return "L${className.replace('.', '/')};"
  }

  private fun getTitle(apkFile: File, className: String): String {
    return "${apkFile.name} (${className.substring(className.lastIndexOf('.') + 1)})"
  }
}
