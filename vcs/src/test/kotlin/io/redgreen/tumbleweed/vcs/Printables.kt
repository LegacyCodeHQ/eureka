package io.redgreen.tumbleweed.vcs

val BlameResult.printable: String
  get() {
    return lines.joinToString(System.lineSeparator()) { line ->
      "${line.commitHash.value} ${line.email.value} ${line.timestamp} ${line.number}) ${line.content}"
    }
  }

val Map<CommitHash, List<BlameLine>>.printable: Map<String, List<Int>>
  get() {
    return mapKeys { (commitHash, _) -> commitHash.value }
      .mapValues { blameLines ->
        blameLines.value.map(BlameLine::number)
      }
  }
