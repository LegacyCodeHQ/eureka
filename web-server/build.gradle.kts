dependencies {
  implementation(projects.bytecodeScanner)
  implementation(projects.filesystem)
  implementation(projects.vcs)
  implementation(projects.android)
  implementation(projects.viz)
  implementation(projects.dex)
  implementation(projects.sidekick)

  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.server.websockets)
  implementation(libs.ktor.server.cors)
  implementation(libs.jproc)
}
