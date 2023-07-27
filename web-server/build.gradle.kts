dependencies {
  implementation(projects.bytecodeScanner)
  implementation(projects.filesystem)
  implementation(projects.vcs)
  implementation(projects.androidClassifier)
  implementation(projects.viz)
  implementation(projects.androidDex)
  implementation(projects.sidekick)

  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.server.websockets)
  implementation(libs.ktor.server.cors)
  implementation(libs.jproc)
}
