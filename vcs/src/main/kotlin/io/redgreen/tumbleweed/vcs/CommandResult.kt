package io.redgreen.tumbleweed.vcs

data class CommandResult(
  val exitCode: Int,
  val output: String,
)
