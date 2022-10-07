package io.redgreen.tumbleweed.web.observablehq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.redgreen.tumbleweed.ClassStructure

val ClassStructure.json: String
  get() {
    return jacksonObjectMapper()
      .writeValueAsString(this)
  }
