package com.legacycode.tumbleweed.web

import com.legacycode.tumbleweed.ClassScanner
import com.legacycode.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import com.legacycode.tumbleweed.web.observablehq.classifiers.MemberClassifier
import com.legacycode.tumbleweed.web.observablehq.toGraph
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue
import org.slf4j.LoggerFactory

sealed interface GraphDataSource {
  val location: File
  val graph: BilevelEdgeBundlingGraph
}

class CompiledClassFile(
  override val location: File,
  private val classifier: MemberClassifier,
) : GraphDataSource {
  private val logger = LoggerFactory.getLogger(CompiledClassFile::class.java)

  @OptIn(ExperimentalTime::class)
  override val graph: BilevelEdgeBundlingGraph
    get() {
      val (classStructure, duration) = measureTimedValue { ClassScanner.scan(location) }
      logger.info("Scanned class file in {}.", duration)
      return classStructure.toGraph(classifier)
    }
}

class JsonFile(override val location: File) : GraphDataSource {
  override val graph: BilevelEdgeBundlingGraph
    get() = BilevelEdgeBundlingGraph.fromJson(location.readText())
}
