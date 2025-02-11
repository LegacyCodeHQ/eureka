package com.legacycode.eureka.vcs

import com.google.common.truth.Truth.assertThat
import com.legacycode.eureka.vcs.blame.BlameCommand
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class GitCommandTest {
  private val simpleAndroidRepo = TestRepo("simple-android")

  @Test
  fun `it should get the HEAD commit hash`() {
    // given
    val command = GitCommand(
      "rev-parse",
      arrayOf("--short", "HEAD"),
      simpleAndroidRepo.path
    )

    // when
    val result = command.execute()

    // then
    assertThat(result.getOrNull())
      .isEqualTo(CommandResult(0, "5eb413173"))
  }

  @Test
  fun `it should get blame output`() {
    // given
    val blameCommand = BlameCommand(
      Repo(simpleAndroidRepo.path),
      RepoFile("app/src/main/java/org/simple/clinic/home/patients/PatientsEffectHandler.kt"),
    )

    // when
    val result = blameCommand.execute()

    // then
    Approvals.verify(result.getOrNull()!!.printable)
  }
}
