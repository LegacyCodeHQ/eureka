package com.legacycode.tumbleweed.vcs.blame

import com.legacycode.tumbleweed.vcs.Repo
import com.legacycode.tumbleweed.vcs.RepoFile
import com.legacycode.tumbleweed.vcs.TestRepo
import com.legacycode.tumbleweed.vcs.commitHashGroupPrintable
import com.legacycode.tumbleweed.vcs.emailGroupPrintable
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
