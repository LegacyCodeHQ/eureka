dependencies {
  implementation("org.buildobjects:jproc:2.8.2")
  implementation("io.arrow-kt:arrow-core:1.1.2")
}

task("cloneTestRepo") {
  fun cloneGitRepo(repoDestination: File, repoUrl: String) {
    val process = Runtime.getRuntime().exec(
      arrayOf(
        "git",
        "clone",
        repoUrl,
        repoDestination.canonicalPath,
      )
    )
    process.waitFor()
  }

  fun checkoutCommit(repoDestination: File, commitHash: String) {
    val process = Runtime.getRuntime().exec(
      arrayOf(
        "git",
        "-C",
        repoDestination.canonicalPath,
        "checkout",
        commitHash,
      )
    )
    process.waitFor()
  }

  val testDataDirectory = File(System.getProperty("user.home")).resolve(".tumbleweed-test-data")
  if (!testDataDirectory.exists()) {
    println("Test data directory does not exist, creating...")
    testDataDirectory.mkdir()
  }

  val testRepoDirectory = testDataDirectory.resolve("simple-android")
  if (!testRepoDirectory.exists()) {
    testRepoDirectory.mkdir()
    println("Cloning test repository...")

    cloneGitRepo(testRepoDirectory, "https://github.com/ragunathjawahar/simple-android.git")
    checkoutCommit(testRepoDirectory, "5eb413173505ceb287a7b0bfb27b698ed556c829")
  }
}
