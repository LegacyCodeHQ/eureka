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

  fun execute(): Either<Nothing, CommandResult> {
    return command.execute()
  }
}
