dependencies {
  implementation(libs.byteBuddy)

  testImplementation(projects.bytecodeSamples)
  testImplementation(projects.bytecodeTesting)
}
