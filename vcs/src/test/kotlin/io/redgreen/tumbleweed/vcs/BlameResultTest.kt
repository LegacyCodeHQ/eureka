package io.redgreen.tumbleweed.vcs

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class BlameResultTest {
  private val simpleAndroidRepo = TestRepo("simple-android")

  @Test
  fun `group result by commits`() {
    // given
    val blameCommand = BlameCommand(
      Repo(simpleAndroidRepo.path),
      RepoFile("app/src/main/java/org/simple/clinic/home/patients/PatientsEffectHandler.kt"),
    )
    val blameResult = blameCommand.execute().orNull()!!

    // when
    val statsByCommitHash = blameResult.byCommitHash()

    // then
    Approvals.verify(statsByCommitHash.printable)
  }
}
