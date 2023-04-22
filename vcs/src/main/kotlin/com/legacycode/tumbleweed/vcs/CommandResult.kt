package com.legacycode.tumbleweed.vcs

data class CommandResult(
  val exitCode: Int,
  val output: String,
)
