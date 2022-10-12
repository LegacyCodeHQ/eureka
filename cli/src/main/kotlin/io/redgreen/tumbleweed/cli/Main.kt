package io.redgreen.tumbleweed.cli

import kotlin.system.exitProcess
import picocli.CommandLine

const val DEFAULT_PORT = 7070

fun main(args: Array<String>) {
  exitProcess(CommandLine(TumbleweedCommand())
    .execute(*args))
}
