package io.redgreen.tumbleweed.vcs.blame

import io.redgreen.tumbleweed.vcs.CommitHash
import io.redgreen.tumbleweed.vcs.Email

class BlameResult(
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> =
    lines.groupBy(BlameLine::commitHash)

  fun byEmail(): Map<Email, List<BlameLine>> =
    lines.groupBy(BlameLine::email)
}
