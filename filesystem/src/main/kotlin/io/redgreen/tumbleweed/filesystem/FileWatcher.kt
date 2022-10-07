package io.redgreen.tumbleweed.filesystem

import com.sun.nio.file.SensitivityWatchEventModifier
import java.nio.file.ClosedWatchServiceException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import java.nio.file.WatchService
import org.slf4j.LoggerFactory

class FileWatcher {
  private val logger = LoggerFactory.getLogger(FileWatcher::class.java)
  private val watchService: WatchService = FileSystems.getDefault().newWatchService()

  fun startWatching(file: Path, onFileChanged: () -> Unit) {
    var watchKey = file.parent.register(
      watchService,
      arrayOf(ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE),
      SensitivityWatchEventModifier.HIGH
    )
    logger.info("Watching for file changes in {}", file.parent)

    Thread {
      while (true) {
        val pollEvents = watchKey.pollEvents()
        for (pollEvent in pollEvents) {
          val fileWithEvent = pollEvent.context() as Path
          logger.debug("WatchEvent: {} on {}", pollEvent.kind(), fileWithEvent)
          onFileChanged()
        }
        watchKey.reset()
        try {
          watchKey = watchService.take()
        } catch (e: ClosedWatchServiceException) {
          logger.info("Stopped watching files")
          break
        }
      }
    }.start()
  }

  fun stopWatching() {
    watchService.close()
  }
}
