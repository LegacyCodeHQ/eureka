package io.redgreen.tumbleweed.vcs.blame

import arrow.core.Either
import io.redgreen.tumbleweed.vcs.CommandResult
import io.redgreen.tumbleweed.vcs.GitCommand
import io.redgreen.tumbleweed.vcs.Repo
import io.redgreen.tumbleweed.vcs.RepoFile

class BlameCommand(
  repo: Repo,
  repoFile: RepoFile,
) {
  private val command = GitCommand(
    "blame",
    arrayOf("-e", repoFile.path),
    repo.path,
  )

  fun execute(): Either<Nothing, BlameResult> {
    return command.execute()
      .map(::blameLines)
      .map(::BlameResult)
  }

  private fun blameLines(commandResult: CommandResult): List<BlameLine> =
    commandResult.output.lines().map(BlameLine.Companion::from)
}
