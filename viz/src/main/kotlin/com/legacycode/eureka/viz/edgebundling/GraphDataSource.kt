package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.ClassScanner
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue
import org.slf4j.LoggerFactory

sealed interface GraphDataSource {
  val location: File
  val graph: EdgeBundlingGraph
}

class CompiledClassFile(
  override val location: File,
  private val classifier: MemberClassifier,
) : GraphDataSource {
  private val logger = LoggerFactory.getLogger(CompiledClassFile::class.java)

  @OptIn(ExperimentalTime::class)
  override val graph: EdgeBundlingGraph
    get() {
      val (classStructure, duration) = measureTimedValue { ClassScanner.scan(location) }
      logger.info("Scanned class file in {}.", duration)
      return classStructure.toGraph(classifier)
    }
}

class JsonFile(override val location: File) : GraphDataSource {
  override val graph: EdgeBundlingGraph
    get() = EdgeBundlingGraph.fromJson(location.readText())
}
