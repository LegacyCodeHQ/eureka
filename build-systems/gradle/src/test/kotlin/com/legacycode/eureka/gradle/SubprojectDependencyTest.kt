package com.legacycode.eureka.gradle

import com.google.common.truth.Truth.assertThat
import com.legacycode.eureka.gradle.testing.TaskOutputResource
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SubprojectDependencyTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "+--- project libsignal-service",
      "+--- project :libsignal-service",
      "+--- project libsignal-service (n)",
      "+--- project libsignal-service (c)",
      "+--- project libsignal-service (*)",
      "+--- project :libsignal-service FAILED",
    ]
  )
  fun `it can parse variations of the subproject dependency patterns`(
    subprojectDependencyLine: String,
  ) {
    // when
    val dependency = SubprojectDependency.from(subprojectDependencyLine)!!

    // then
    assertThat(dependency.name)
      .isEqualTo("libsignal-service")
  }

  @Test
  fun `it does not parse variation that are not on level 1`() {
    // given
    val subprojectDependencyLine = "|    +--- project :core-util (*)"

    // when
    val dependency = SubprojectDependency.from(subprojectDependencyLine)

    // then
    assertThat(dependency)
      .isNull()
  }

  @Test
  fun `it can parse project dependencies from task output`() {
    // given
    val dependenciesOutput = TaskOutputResource.dependencies("Signal-Android.txt").output

    // when
    val subprojectDependencies = SubprojectDependency.from(dependenciesOutput)

    // then
    val subprojectDependenciesList = subprojectDependencies.joinToString("\n") { it.name }
    Approvals.verify(subprojectDependenciesList)
  }
}
