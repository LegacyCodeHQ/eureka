dependencies {
  implementation(project(":bytecode-scanner"))
  implementation(project(":filesystem"))
  implementation(project(":vcs"))
  implementation(project(":android"))
  implementation(project(":viz"))

  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.server.websockets)
  implementation(libs.ktor.server.cors)
}
