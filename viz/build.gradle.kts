dependencies {
  implementation(projects.bytecodeScanner)

  implementation(libs.jackson.kotlin)

  testImplementation(projects.bytecodeSamples)
  testImplementation(projects.bytecodeTesting)
}
