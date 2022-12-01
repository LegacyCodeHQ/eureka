package io.redgreen.tumbleweed.vcs.blame

import io.redgreen.tumbleweed.vcs.Repo
import io.redgreen.tumbleweed.vcs.RepoFile
import io.redgreen.tumbleweed.vcs.TestRepo
import io.redgreen.tumbleweed.vcs.commitHashGroupPrintable
import io.redgreen.tumbleweed.vcs.emailGroupPrintable
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class BlameResultTest {
  private val simpleAndroidRepo = TestRepo("simple-android")
  private val blameCommand = BlameCommand(
    Repo(simpleAndroidRepo.path),
    RepoFile("app/src/main/java/org/simple/clinic/home/patients/PatientsEffectHandler.kt"),
  )

  @Test
  fun `group result by commits`() {
    // given
    val blameResult = blameCommand.execute().orNull()!!

    // when
    val statsByCommitHash = blameResult.byCommitHash()

    // then
    Approvals.verify(statsByCommitHash.commitHashGroupPrintable)
  }

  @Test
  fun `group result by email`() {
    // given
    val blameResult = blameCommand.execute().orNull()!!

    // when
    val statsByEmail = blameResult.byEmail()

    // then
    Approvals.verify(statsByEmail.emailGroupPrintable)
  }
}
