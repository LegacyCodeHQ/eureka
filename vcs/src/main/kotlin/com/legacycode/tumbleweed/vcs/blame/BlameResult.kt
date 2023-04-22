package com.legacycode.tumbleweed.vcs.blame

import com.legacycode.tumbleweed.vcs.CommitHash
import com.legacycode.tumbleweed.vcs.Email
import com.legacycode.tumbleweed.vcs.RepoFile

class BlameResult(
  val repoFile: RepoFile,
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> =
    lines.groupBy(BlameLine::commitHash)

  fun byEmail(): Map<Email, List<BlameLine>> =
    lines.groupBy(BlameLine::email)
}
