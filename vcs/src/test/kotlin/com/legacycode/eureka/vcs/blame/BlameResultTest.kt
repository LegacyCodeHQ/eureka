package com.legacycode.eureka.vcs.blame

import com.legacycode.eureka.vcs.Repo
import com.legacycode.eureka.vcs.RepoFile
import com.legacycode.eureka.vcs.TestRepo
import com.legacycode.eureka.vcs.commitHashGroupPrintable
import com.legacycode.eureka.vcs.emailGroupPrintable
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
    val blameResult = blameCommand.execute().getOrNull()!!

    // when
    val statsByCommitHash = blameResult.byCommitHash()

    // then
    Approvals.verify(statsByCommitHash.commitHashGroupPrintable)
  }

  @Test
  fun `group result by email`() {
    // given
    val blameResult = blameCommand.execute().getOrNull()!!

    // when
    val statsByEmail = blameResult.byEmail()

    // then
    Approvals.verify(statsByEmail.emailGroupPrintable)
  }
}
