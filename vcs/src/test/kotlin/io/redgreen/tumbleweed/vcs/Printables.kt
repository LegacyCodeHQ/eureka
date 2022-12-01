package io.redgreen.tumbleweed.vcs

val BlameResult.printable: String
  get() {
    return lines.joinToString(System.lineSeparator()) { line ->
      "${line.commitHash.value} ${line.email.address} ${line.timestamp} ${line.number}) ${line.content}"
    }
  }

val Map<CommitHash, List<BlameLine>>.commitHashGroupPrintable: Map<String, List<Int>>
  get() {
    return mapKeys { (commitHash, _) -> commitHash.value }
      .mapValues { commitHashToBlameLines ->
        commitHashToBlameLines.value.map(BlameLine::number)
      }
  }

val Map<Email, List<BlameLine>>.emailGroupPrintable: Map<String, List<Int>>
  get() {
    return mapKeys { (email, _) -> email.address }
      .mapValues { emailToBlameLines ->
        emailToBlameLines.value.map(BlameLine::number)
      }
  }
