package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.web.TumbleweedServer
import kotlin.system.exitProcess
import picocli.CommandLine

fun main(args: Array<String>) {
  TumbleweedServer().start()

  return
  exitProcess(CommandLine(TumbleweedCommand())
    .execute(*args))
}
