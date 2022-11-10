package io.redgreen.tumbleweed.web

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import io.redgreen.tumbleweed.web.observablehq.graph
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue
import org.slf4j.LoggerFactory

sealed interface Source {
  val location: File
  val graph: BilevelEdgeBundlingGraph
}

class CompiledClassFile(override val location: File) : Source {
  private val logger = LoggerFactory.getLogger(CompiledClassFile::class.java)

  @OptIn(ExperimentalTime::class)
  override val graph: BilevelEdgeBundlingGraph
    get() {
      val (classStructure, duration) = measureTimedValue { ClassScanner.scan(location) }
      logger.info("Scanned class file in {}.", duration)
      return classStructure.graph
    }
}

class JsonFile(override val location: File) : Source {
  override val graph: BilevelEdgeBundlingGraph
    get() = BilevelEdgeBundlingGraph.fromJson(location.readText())
}
