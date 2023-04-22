package com.legacycode.tumbleweed.cli.dev.diff

import com.legacycode.tumbleweed.cli.dev.convert.from
import com.legacycode.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class EdgeBundlingGraphExtension : ParameterResolver {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  @Retention(AnnotationRetention.RUNTIME)
  annotation class Graph(val csvFilename: String)

  override fun supportsParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext,
  ): Boolean {
    return parameterContext.parameter.type == BilevelEdgeBundlingGraph::class.java &&
      parameterContext.parameter.annotations.any { it is Graph }
  }

  override fun resolveParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext,
  ): BilevelEdgeBundlingGraph {
    val filename = parameterContext.parameter.annotations
      .find { it is Graph }!!
      .let { it as Graph }.csvFilename
    val resourcePath = listOf("", "diff", filename)
      .joinToString(System.getProperty("file.separator"))
    val csv = EdgeBundlingGraphExtension::class.java.getResource(resourcePath)!!
      .readText()

    return BilevelEdgeBundlingGraph.from(csv)
  }
}
