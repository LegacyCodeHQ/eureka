package io.redgreen.tumbleweed.filesystem

import com.google.common.truth.Truth.assertThat
import java.nio.file.Path
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.io.path.writeText
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

class FileWatcherTest {
  private val fileWatcher = FileWatcher()
  private val fileChangedEventQueue = ArrayBlockingQueue<Boolean>(1)
  private val fileChangedCallback = { fileChangedEventQueue.put(true) }

  @Test
  fun `it should watch for file changes`(@TempDir directory: Path) {
    // given
    val filename = "file.txt"
    val fileToWatch = directory.resolve(filename).also { it.toFile().writeText("Hello, world!") }
    fileWatcher.startWatching(fileToWatch, fileChangedCallback)

    // when
    fileToWatch.writeText("Hello, world! How are you?")

    // then
    assertThat(fileChangedEventQueue.take())
      .isTrue()
  }

  @Test
  fun `it should not receive notifications after stop watching for changes`(@TempDir directory: Path) {
    // given
    val filename = "file.txt"
    val fileToWatch = directory.resolve(filename).also { it.toFile().writeText("Hello, world!") }
    fileWatcher.startWatching(fileToWatch, fileChangedCallback)

    // when
    fileWatcher.stopWatching()
    fileToWatch.writeText("Hello, world! How are you?")

    // then
    assertThat(fileChangedEventQueue.poll(3, TimeUnit.SECONDS))
      .isNull()
  }

  @Test
  fun `it should only send notifications for the file that is being watched`(@TempDir directory: Path) {
    // given
    val filenameA = "file-a.txt"
    val filenameB = "file-b.txt"
    val fileToWatch = directory.resolve(filenameA).also { it.toFile().writeText("Hello, A!") }
    val fileNotToWatch = directory.resolve(filenameB).also { it.toFile().writeText("Hello, B!") }

    fileWatcher.startWatching(fileToWatch, fileChangedCallback)

    // when
    fileNotToWatch.writeText("Sssshhh…")

    // then
    assertThat(fileChangedEventQueue.poll(3, TimeUnit.SECONDS))
      .isNull()
  }
}
