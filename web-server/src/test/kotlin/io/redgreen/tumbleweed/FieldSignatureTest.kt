package io.redgreen.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class FieldSignatureTest {
  @Test
  internal fun `concise discards the outer class names`() {
    // given
    val fieldSignature = FieldSignature("apiKey", QualifiedType("io.redgreen.twd.Secure\$ApiKey"))

    // when & then
    assertThat(fieldSignature.concise)
      .isEqualTo("ApiKey apiKey")
  }
}
