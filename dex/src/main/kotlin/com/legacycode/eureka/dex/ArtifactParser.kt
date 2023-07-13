package com.legacycode.eureka.dex

import java.io.File

interface ArtifactParser {
  val file: File

  fun inheritanceAdjacencyList(): InheritanceAdjacencyList
}
