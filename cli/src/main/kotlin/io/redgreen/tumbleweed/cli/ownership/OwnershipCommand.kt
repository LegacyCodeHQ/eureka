package io.redgreen.tumbleweed.cli.ownership

import picocli.CommandLine.Command

@Command(
  name = "ownership",
  description = ["see ownership information of a given file in your browser"],
)
class OwnershipCommand : Runnable {
  override fun run() {
    println("Who owns how much?")
  }
}
