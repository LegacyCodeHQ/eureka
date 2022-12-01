package io.redgreen.tumbleweed.vcs

class BlameResult(
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> {
    return lines.groupBy(BlameLine::commitHash)
  }
}
