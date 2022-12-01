package io.redgreen.tumbleweed.vcs.blame

import io.redgreen.tumbleweed.vcs.CommitHash
import io.redgreen.tumbleweed.vcs.Email
import io.redgreen.tumbleweed.vcs.RepoFile

class BlameResult(
  val repoFile: RepoFile,
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> =
    lines.groupBy(BlameLine::commitHash)

  fun byEmail(): Map<Email, List<BlameLine>> =
    lines.groupBy(BlameLine::email)
}
