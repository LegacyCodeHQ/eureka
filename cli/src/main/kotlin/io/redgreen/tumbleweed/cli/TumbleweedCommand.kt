package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.JsonCommand
import io.redgreen.tumbleweed.cli.commands.WatchCommand
import picocli.CommandLine.Command

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
    JsonCommand::class,
  ],
)
class TumbleweedCommand
