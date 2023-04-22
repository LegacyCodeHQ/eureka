package com.legacycode.tumbleweed.vcs.blame

import arrow.core.Either
import com.legacycode.tumbleweed.vcs.CommandResult
import com.legacycode.tumbleweed.vcs.GitCommand
import com.legacycode.tumbleweed.vcs.Repo
import com.legacycode.tumbleweed.vcs.RepoFile

class BlameCommand(
  repo: Repo,
  private val repoFile: RepoFile,
) {
  private val command = GitCommand(
    "blame",
    arrayOf("-e", repoFile.path),
    repo.path,
  )

  fun execute(): Either<Nothing, BlameResult> {
    return command.execute()
      .map(::blameLines)
      .map { blameLines -> BlameResult(repoFile, blameLines) }
  }

  private fun blameLines(commandResult: CommandResult): List<BlameLine> =
    commandResult.output.lines().map(BlameLine.Companion::from)
}
