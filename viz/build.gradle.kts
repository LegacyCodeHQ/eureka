dependencies {
  implementation(projects.bytecodeScanner)

  implementation(libs.jackson.kotlin)

  testImplementation(projects.bytecodeSamplesKotlin)
  testImplementation(projects.bytecodeTesting)
}
