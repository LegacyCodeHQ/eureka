package io.redgreen.tumbleweed.vcs

class BlameResult(
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> =
    lines.groupBy(BlameLine::commitHash)

  fun byEmail(): Map<Email, List<BlameLine>> =
    lines.groupBy(BlameLine::email)
}
