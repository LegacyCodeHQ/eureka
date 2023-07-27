package com.legacycode.eureka.cli.dev.diff

import com.legacycode.eureka.cli.dev.convert.from
import com.legacycode.eureka.testing.TextResource
import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph
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
    return parameterContext.parameter.type == EdgeBundlingGraph::class.java &&
      parameterContext.parameter.annotations.any { it is Graph }
  }

  override fun resolveParameter(
    parameterContext: ParameterContext,
    extensionContext: ExtensionContext,
  ): EdgeBundlingGraph {
    val filename = parameterContext.parameter.annotations
      .find { it is Graph }!!
      .let { it as Graph }.csvFilename
    val resourcePath = listOf("", "diff", filename)
      .joinToString(System.getProperty("file.separator"))
    val csv = TextResource(resourcePath).content

    return EdgeBundlingGraph.from(csv)
  }
}
