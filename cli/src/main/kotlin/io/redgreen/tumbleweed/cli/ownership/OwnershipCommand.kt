package io.redgreen.tumbleweed.cli.ownership

import com.legacycode.tumbleweed.web.owneship.OwnershipServer
import java.io.File
import picocli.CommandLine
import picocli.CommandLine.Command

@Command(
  name = "ownership",
  description = ["see ownership information of a given file in your browser"],
)
class OwnershipCommand : Runnable {
  companion object {
    private const val DEFAULT_PORT = 7080
  }

  @CommandLine.Option(
    names = ["-r", "--repo"],
    description = ["path to the git repo"],
    required = true,
  )
  lateinit var repoDir: File

  override fun run() {
    OwnershipServer().start(repoDir.path, DEFAULT_PORT)
  }
}
