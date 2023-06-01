dependencies {
  implementation(libs.byteBuddy)

  testImplementation(project(":bytecode-samples"))
  testImplementation(project(":bytecode-testing"))
}
