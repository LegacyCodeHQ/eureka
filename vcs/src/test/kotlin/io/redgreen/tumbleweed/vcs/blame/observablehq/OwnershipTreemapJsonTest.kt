package io.redgreen.tumbleweed.vcs.blame.observablehq

import io.redgreen.tumbleweed.vcs.Repo
import io.redgreen.tumbleweed.vcs.RepoFile
import io.redgreen.tumbleweed.vcs.TestRepo
import io.redgreen.tumbleweed.vcs.blame.BlameCommand
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

class OwnershipTreemapJsonTest {
  @Test
  fun `it should generate treemap data from blame result`() {
    // given
    val simpleAndroidRepo = TestRepo("simple-android")
    val blameCommand = BlameCommand(
      Repo(simpleAndroidRepo.path),
      RepoFile("app/src/main/java/org/simple/clinic/home/patients/PatientsEffectHandler.kt"),
    )
    val blameResult = blameCommand.execute().orNull()!!

    // when
    val ownershipTreemap = OwnershipTreemapJson.from(blameResult)

    // then
    JsonApprovals.verifyJson(ownershipTreemap.toJson())
  }
}
