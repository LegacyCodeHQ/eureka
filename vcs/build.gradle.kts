dependencies {
  api(libs.arrow.core)

  implementation(libs.jproc)
  implementation(libs.jackson.kotlin)
}

val cloneTestRepo by tasks.registering {
  val testDataDirectory = File(System.getProperty("user.home")).resolve(".eureka-test-data")
  val testRepoDirectory = testDataDirectory.resolve("simple-android")
  val commitHash = "5eb413173505ceb287a7b0bfb27b698ed556c829"

  outputs.dir(testRepoDirectory)

  doLast {
    testDataDirectory.mkdirs()

    if (!testRepoDirectory.resolve(".git").exists()) {
      delete(testRepoDirectory)
      exec {
        commandLine(
          "git",
          "clone",
          "https://github.com/ragunathjawahar/simple-android.git",
          testRepoDirectory.canonicalPath,
        )
      }
    }

    exec {
      commandLine(
        "git",
        "-C",
        testRepoDirectory.canonicalPath,
        "checkout",
        commitHash,
      )
    }
  }
}

tasks.named<Test>("test") {
  dependsOn(cloneTestRepo)
}
