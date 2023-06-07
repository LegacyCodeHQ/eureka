package com.legacycode.eureka.vcs

import java.io.File

class TestRepo(private val name: String) {
  val path: String
    get() {
      val userHome = System.getProperty("user.home")
      return File(userHome)
        .resolve(".tumbleweed-test-data")
        .resolve(name)
        .canonicalPath
    }
}
