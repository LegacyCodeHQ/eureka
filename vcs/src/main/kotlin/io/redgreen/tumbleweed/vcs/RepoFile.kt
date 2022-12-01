package io.redgreen.tumbleweed.vcs

@JvmInline
value class RepoFile(val path: String) {
  val name: String
    get() {
      return path.substring(path.lastIndexOf('/') + 1)
    }
}
