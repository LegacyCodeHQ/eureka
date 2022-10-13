package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.dev.convert.ConvertCommand
import io.redgreen.tumbleweed.cli.dev.diff.DiffCommand
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
    ConvertCommand::class,
    DiffCommand::class,
  ],
)
class TumbleweedCommand

fun main(args: Array<String>) {
  val status = CommandLine(TumbleweedCommand())
    .execute(*args)
  exitProcess(status)
}
