package io.redgreen.tumbleweed.vcs

import arrow.core.Either
import org.buildobjects.process.ProcBuilder

class GitCommand(
  private val command: String,
  private val options: Array<String>,
) {
  fun execute(): Either<Nothing, CommandResult> {
    val output = ProcBuilder.run("git", command, *options)
    return Either.Right(CommandResult(0, output.trim()))
  }
}
