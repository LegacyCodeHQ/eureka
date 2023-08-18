package com.legacycode.eureka.filesystem

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
  fun `it should send notifications for any changes within the directory`(@TempDir tempDirectory: Path) {
    // given
    val fileToWatch = tempDirectory.createFile("file-a.txt", "Hello, A!")
    val fileInsideTheSameDirectory = tempDirectory.createFile("file-b.txt", "Hello, B!")

    fileWatcher.startWatching(fileToWatch, fileModifiedCallback)

    // when
    fileInsideTheSameDirectory.writeText("Sssshhh…")

    // then
    assertThat(fileModifiedEventQueue.poll(3, TimeUnit.SECONDS))
      .isTrue()
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
