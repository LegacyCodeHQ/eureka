rootProject.name = "eureka"
include(
  ":cli",
  ":bytecode-scanner",
  ":bytecode-samples-java",
  ":web-server",
  ":filesystem",
  ":vcs",
  ":bytecode-testing",
  ":web-client-react",
  ":android",
  ":viz",
  ":build-systems-gradle",
  ":selenium",
  ":dex",
  ":bytecode-scanner-tests",
  ":bytecode-samples-kotlin",
  ":sidekick",
)

project(":bytecode-scanner").projectDir = file("bytecode/scanner")
project(":bytecode-samples-java").projectDir = file("bytecode/samples-java")
project(":bytecode-testing").projectDir = file("bytecode/testing")

project(":build-systems-gradle").projectDir = file("build-systems/gradle")

project(":bytecode-scanner-tests").projectDir = file("bytecode/scanner-tests")
project(":bytecode-samples-kotlin").projectDir = file("bytecode/samples-kotlin")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      library("picocli", "info.picocli:picocli:4.7.4")
      library("logback", "ch.qos.logback:logback-classic:1.4.8")
      library("commonsCsv", "org.apache.commons:commons-csv:1.10.0")
      library("jproc", "org.buildobjects:jproc:2.8.2")
      library("jackson-kotlin", "com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
      library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
      library("byteBuddy", "net.bytebuddy:byte-buddy:1.14.5")

      library("ktor-server-netty", "io.ktor:ktor-server-netty:2.3.2")
      library("ktor-server-websockets", "io.ktor:ktor-server-websockets:2.3.2")
      library("ktor-server-cors", "io.ktor:ktor-server-cors:2.3.2")

      library("picnic", "com.jakewharton.picnic:picnic:0.6.0")
      library("dexlib2", "org.smali:dexlib2:2.5.2")
      library("selenium", "org.seleniumhq.selenium:selenium-java:4.10.0")
    }

    create("testLibs") {
      // JUnit
      val junitVersion = "5.10.0"
      library("junit-api", "org.junit.jupiter:junit-jupiter-api:$junitVersion")
      library("junit-params", "org.junit.jupiter:junit-jupiter-params:$junitVersion")
      library("junit-engine", "org.junit.jupiter:junit-jupiter-engine:$junitVersion")
      bundle("junit", listOf("junit-api", "junit-params", "junit-engine"))

      // assertion libraries
      library("truth", "com.google.truth:truth:1.1.5")

      library("approvalTests", "com.approvaltests:approvaltests:18.7.1")
      library("gson", "com.google.code.gson:gson:2.10.1")
    }
  }
}
