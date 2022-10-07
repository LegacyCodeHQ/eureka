package io.redgreen.tumbleweed.cli.filesystem

import com.sun.nio.file.SensitivityWatchEventModifier
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import java.nio.file.WatchService

class FileWatcher {
  private val watchService: WatchService = FileSystems.getDefault().newWatchService()

  fun startWatching(file: Path, onFileChanged: () -> Unit) {
    var watchKey = file.parent.register(
      watchService,
      arrayOf(ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE),
      SensitivityWatchEventModifier.HIGH
    )
    println("Watching for changes in ${file.parent}")

    Thread {
      while (true) {
        val pollEvents = watchKey.pollEvents()
        for (pollEvent in pollEvents) {
          val fileWithEvent = pollEvent.context() as Path
          println("Event: ${pollEvent.kind()} on $fileWithEvent")
          onFileChanged()
        }
        watchKey.reset()
        watchKey = watchService.take()
      }
    }.start()
  }
}
