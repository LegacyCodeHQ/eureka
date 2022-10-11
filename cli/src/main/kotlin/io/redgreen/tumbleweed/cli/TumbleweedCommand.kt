package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.WatchCommand
import io.redgreen.tumbleweed.cli.dev.JsonCommand
import picocli.CommandLine.Command

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
    JsonCommand::class,
  ],
)
class TumbleweedCommand
