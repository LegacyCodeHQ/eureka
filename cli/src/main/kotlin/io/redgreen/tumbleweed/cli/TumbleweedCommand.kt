package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.WatchCommand
import picocli.CommandLine.Command

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
  ],
)
class TumbleweedCommand
