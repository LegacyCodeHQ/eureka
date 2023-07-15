package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class DescriptorTest {
  @Test
  fun `it should not match when type name does not have the keyword`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")

    // when & then
    assertThat(descriptor.matches("Celebrate"))
      .isFalse()
  }

  @Test
  fun `it should match when type name contains the keyword`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")

    // when & then
    assertThat(descriptor.matches("Help"))
      .isTrue()
  }

  @Test
  fun `it should match when type name contains the keyword (ignore case)`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")

    // when & then
    assertThat(descriptor.matches("help"))
      .isTrue()
  }

  @Test
  fun `it should match when nested type contains the keyword`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")

    // when & then
    assertThat(descriptor.matches("Help"))
      .isTrue()
  }

  @Test
  fun `it should match when nested type contains the keyword (ignore case)`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")

    // when & then
    assertThat(descriptor.matches("help"))
      .isTrue()
  }

  @Test
  fun `it should not match when nested type does not contain the keyword`() {
    // given
    val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderRepository;")

    // when & then
    assertThat(descriptor.matches("help"))
      .isFalse()
  }
}
