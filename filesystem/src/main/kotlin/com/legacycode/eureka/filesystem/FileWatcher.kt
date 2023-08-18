package com.legacycode.eureka.filesystem

import com.sun.nio.file.SensitivityWatchEventModifier.HIGH
import java.nio.file.ClosedWatchServiceException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import java.nio.file.WatchKey
import java.nio.file.WatchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FileWatcher {
  private val logger = LoggerFactory.getLogger(FileWatcher::class.java)

  private val watchService: WatchService = FileSystems.getDefault().newWatchService()
  private val eventsToWatchFor = arrayOf(ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)

  fun startWatching(file: Path, onFileModified: () -> Unit) {
    val watchKey = file.parent.register(watchService, eventsToWatchFor, HIGH)
    logger.info("Watching for file changes in {}", file.parent)
    Thread(PollEventsRunnable(watchService, watchKey, onFileModified)).start()
  }

  fun stopWatching() {
    watchService.close()
  }
}

class PollEventsRunnable(
  private val watchService: WatchService,
  private val initialWatchKey: WatchKey,
  private val onFileChanged: () -> Unit,
) : Runnable {
  private val logger: Logger = LoggerFactory.getLogger(PollEventsRunnable::class.java)

  override fun run() {
    var watchKey = initialWatchKey
    while (true) {
      val pollEvents = watchKey.pollEvents()
      for (pollEvent in pollEvents) {
        val affectedFile = pollEvent.context() as Path
        logger.debug("WatchEvent: {} on {}", pollEvent.kind(), affectedFile)
        onFileChanged()
      }
      watchKey.reset()

      try {
        watchKey = watchService.take()
      } catch (e: ClosedWatchServiceException) {
        logger.info("Stopped watching files", e)
        break
      }
    }
  }
}
