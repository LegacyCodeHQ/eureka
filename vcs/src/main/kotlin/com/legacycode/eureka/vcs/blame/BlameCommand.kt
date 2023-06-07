package com.legacycode.eureka.vcs.blame

import arrow.core.Either
import com.legacycode.eureka.vcs.CommandResult
import com.legacycode.eureka.vcs.GitCommand
import com.legacycode.eureka.vcs.Repo
import com.legacycode.eureka.vcs.RepoFile

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
