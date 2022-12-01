package io.redgreen.tumbleweed.vcs

import arrow.core.Either
import org.buildobjects.process.ProcBuilder
import org.slf4j.LoggerFactory

class GitCommand(
  private val command: String,
  private val options: Array<String>,
  private val repoPath: String? = null,
) {
  companion object {
    private val logger = LoggerFactory.getLogger(GitCommand::class.java)
  }

  fun execute(): Either<Nothing, CommandResult> {
    val args = if (repoPath == null) {
      arrayOf(command) + options
    } else {
      arrayOf("-C", repoPath, command) + options
    }
    logger.info("Running command: 'git {}'", args.joinToString(" "))
    val output = ProcBuilder.run("git", *args)
    return Either.Right(CommandResult(0, output.trim()))
  }
}
