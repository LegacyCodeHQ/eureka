package io.redgreen.tumbleweed.vcs

val BlameResult.printable: String
  get() {
    return lines.joinToString(System.lineSeparator()) { line ->
      "${line.commitHash.value} ${line.email.value} ${line.timestamp} ${line.number}) ${line.content}"
    }
  }
