package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.dev.json.JsonCommand
import io.redgreen.tumbleweed.cli.dev.view.ViewCommand
import io.redgreen.tumbleweed.cli.watch.WatchCommand
import kotlin.system.exitProcess
import picocli.CommandLine
import picocli.CommandLine.Command

const val DEFAULT_PORT = 7070

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
    JsonCommand::class,
    ViewCommand::class,
  ],
)
class TumbleweedCommand

fun main(args: Array<String>) {
  exitProcess(CommandLine(TumbleweedCommand())
    .execute(*args))
}
