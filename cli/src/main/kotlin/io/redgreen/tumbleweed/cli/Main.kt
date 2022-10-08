package io.redgreen.tumbleweed.cli

import kotlin.system.exitProcess
import picocli.CommandLine

fun main(args: Array<String>) {
  exitProcess(CommandLine(TumbleweedCommand())
    .execute(*args))
}
