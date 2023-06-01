dependencies {
  implementation(projects.bytecodeScanner)
  implementation(projects.filesystem)
  implementation(projects.vcs)
  implementation(projects.android)
  implementation(projects.viz)

  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.server.websockets)
  implementation(libs.ktor.server.cors)
}
