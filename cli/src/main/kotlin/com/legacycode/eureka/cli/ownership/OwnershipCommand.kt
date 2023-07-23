package com.legacycode.eureka.cli.ownership

import com.legacycode.eureka.web.ownership.OwnershipServer
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
  name = "ownership",
  description = ["visualize file ownership information in your browser"],
)
class OwnershipCommand : Runnable {
  companion object {
    private const val DEFAULT_PORT = 7080
  }

  @Option(
    names = ["-r", "--repo"],
    description = ["path to the git repo"],
    required = false,
  )
  var repoDir: Path = Path("")

  override fun run() {
    OwnershipServer().start(repoDir.pathString, DEFAULT_PORT)
  }
}
