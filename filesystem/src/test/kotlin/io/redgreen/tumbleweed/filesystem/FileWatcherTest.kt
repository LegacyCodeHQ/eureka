package io.redgreen.tumbleweed.filesystem

import com.google.common.truth.Truth.assertThat
import java.nio.file.Path
import kotlin.io.path.writeText
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

class FileWatcherTest {
  private val fileWatcher = FileWatcher()

  @Test
  fun `it should watch for file changes`(@TempDir directory: Path) {
    // given
    val filename = "file.txt"
    val fileToWatch = directory.resolve(filename).also { it.toFile().writeText("Hello, world!") }
    var fileChanged = false

    fileWatcher.startWatching(fileToWatch) { fileChanged = true }

    // when
    fileToWatch.writeText("Hello, world! How are you?")
    Thread.sleep(3000)

    // then
    assertThat(fileChanged)
      .isTrue()
  }

  @Test
  fun `it should not receive notifications after stop watching for changes`(@TempDir directory: Path) {
    // given
    val filename = "file.txt"
    val fileToWatch = directory.resolve(filename).also { it.toFile().writeText("Hello, world!") }
    var fileChanged = false

    fileWatcher.startWatching(fileToWatch) { fileChanged = true }

    // when
    fileWatcher.stopWatching()
    fileToWatch.writeText("Hello, world! How are you?")
    Thread.sleep(3000)

    // then
    assertThat(fileChanged)
      .isFalse()
  }

  @Test
  fun `it should only send notifications for the file that is being watched`(@TempDir directory: Path) {
    // given
    val filenameA = "file-a.txt"
    val filenameB = "file-b.txt"
    val fileToWatch = directory.resolve(filenameA).also { it.toFile().writeText("Hello, A!") }
    val fileNotToWatch = directory.resolve(filenameB).also { it.toFile().writeText("Hello, B!") }
    var fileChanged = false

    fileWatcher.startWatching(fileToWatch) { fileChanged = true }

    // when
    fileNotToWatch.writeText("Sssshhh…")
    Thread.sleep(3000)

    // then
    assertThat(fileChanged)
      .isFalse()
  }
}
