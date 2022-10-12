package io.redgreen.tumbleweed.web

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.web.observablehq.json
import java.io.File

sealed interface Source {
  val location: File
  val json: String
}

class CompiledClassFile(override val location: File) : Source {
  override val json: String
    get() = ClassScanner.scan(location).json
}

class JsonFile(override val location: File) : Source {
  override val json: String
    get() = location.readText()
}
