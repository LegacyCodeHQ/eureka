package io.redgreen.tumbleweed.vcs

import arrow.core.Either

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
    commandResult.output.lines().map { rawBlameLine -> BlameLine.from(rawBlameLine) }
}
