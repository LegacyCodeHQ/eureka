package com.legacycode.tumbleweed

import com.google.common.truth.Truth.assertThat
import io.redgreen.tumbleweed.FieldSignature
import io.redgreen.tumbleweed.QualifiedType
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
