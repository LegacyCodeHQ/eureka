package com.legacycode.eureka.vcs.blame

import com.legacycode.eureka.vcs.CommitHash
import com.legacycode.eureka.vcs.Email
import com.legacycode.eureka.vcs.RepoFile

class BlameResult(
  val repoFile: RepoFile,
  val lines: List<BlameLine>,
) {
  fun byCommitHash(): Map<CommitHash, List<BlameLine>> =
    lines.groupBy(BlameLine::commitHash)

  fun byEmail(): Map<Email, List<BlameLine>> =
    lines.groupBy(BlameLine::email)
}
