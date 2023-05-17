import com.github.gradle.node.npm.task.NpmTask

apply(plugin = "com.github.node-gradle.node")

tasks.register<NpmTask>("runBuild") {
  description = "Creates an optimized production build."
  args.set(listOf("run", "build"))
}

tasks.register<Copy>("copyWebClientToServer") {
  description = "Builds and copies the web client to the web-server module."
  dependsOn("runBuild", "copyCss", "copyJs", "copyIndexHtml")
}

val webClientBuildDirectory = File("./build/")
val webClientStaticDirectory = File("./build/static/")

val webServerResourcesDirectory = project.rootDir.resolve(File("web-server/src/main/resources/"))
val webServerResourcesStaticDirectory = project.rootDir.resolve(File("web-server/src/main/resources/static"))

tasks.register<Copy>("copyCss") {
  from(webClientStaticDirectory.resolve("css")) {
    include("main.*.css")
  }
  into(webServerResourcesStaticDirectory.resolve("css"))
}

tasks.register<Copy>("copyJs") {
  from(webClientStaticDirectory.resolve("js")) {
    include("main.*.js")
  }
  into(webServerResourcesStaticDirectory.resolve("js"))
}

tasks.register<Copy>("copyIndexHtml") {
  from(webClientBuildDirectory) {
    include("index.html")
  }
  into(webServerResourcesDirectory)
}
