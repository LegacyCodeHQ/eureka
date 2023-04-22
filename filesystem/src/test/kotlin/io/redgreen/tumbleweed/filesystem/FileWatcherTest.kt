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
  private val fileModifiedEventQueue = ArrayBlockingQueue<Boolean>(1)
  private val fileModifiedCallback = { fileModifiedEventQueue.put(true) }

  @Test
  fun `it should watch for file changes`(@TempDir tempDirectory: Path) {
    // given
    val fileToWatch = tempDirectory.createFile("file.txt", "Hello, world!")
    fileWatcher.startWatching(fileToWatch, fileModifiedCallback)

    // when
    fileToWatch.writeText("Hello, world! How are you?")

    // then
    assertThat(fileModifiedEventQueue.take())
      .isTrue()
  }

  @Test
  fun `it should not receive notifications after stop watching for changes`(@TempDir tempDirectory: Path) {
    // given
    val fileToWatch = tempDirectory.createFile("file.txt", "Hello, world!")
    fileWatcher.startWatching(fileToWatch, fileModifiedCallback)

    // when
    fileWatcher.stopWatching()
    fileToWatch.writeText("Hello, world! How are you?")

    // then
    assertThat(fileModifiedEventQueue.poll(3, TimeUnit.SECONDS))
      .isNull()
  }

  @Test
  fun `it should only send notifications for the file that is being watched`(@TempDir tempDirectory: Path) {
    // given
    val fileToWatch = tempDirectory.createFile("file-a.txt", "Hello, A!")
    val fileNotToWatch = tempDirectory.createFile("file-b.txt", "Hello, B!")

    fileWatcher.startWatching(fileToWatch, fileModifiedCallback)

    // when
    fileNotToWatch.writeText("Sssshhh…")

    // then
    assertThat(fileModifiedEventQueue.poll(3, TimeUnit.SECONDS))
      .isNull()
  }

  private fun Path.createFile(
    filename: String,
    content: String,
  ): Path {
    return resolve(filename).also {
      it.toFile().writeText(content)
    }
  }
}
