package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DescriptorTest {
  @Nested
  inner class RegularMatch {
    @Test
    fun `it should not match when the type does not have the search term`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")

      // when & then
      assertThat(descriptor.matches("Celebrate"))
        .isFalse()
    }

    @Test
    fun `it should match when type name contains the search term`() {
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
    fun `it should not match when the package name contains the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg/wikipedia/settings/languages/WikipediaLanguagesActivity;")

      // when & then
      assertThat(descriptor.matches("settings"))
        .isFalse()
    }
  }

  @Nested
  inner class RegularMatchForNestedType {
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
    fun `it should not match when the nested type does not contain the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderRepository;")

      // when & then
      assertThat(descriptor.matches("help"))
        .isFalse()
    }
  }

  @Nested
  inner class ExactMatch {
    @Test
    fun `it should match when type name contains the exact terminology`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")

      // when & then
      assertThat(descriptor.matches("exact:help"))
        .isTrue()
    }

    @Test
    fun `it should not match if the type name does not contain the exact terminology 'exact' prefix`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")

      // when & then
      assertThat(descriptor.matches("exact:set"))
        .isFalse()
    }

    @Test
    fun `it should match if the type name contains the exact terminology when using 'exact' prefix`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")

      // when & then
      assertThat(descriptor.matches("exact:settings"))
        .isTrue()
    }

    @Test
    fun `it should ignore case in the 'exact' directive`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")

      // when & then
      assertThat(descriptor.matches("eXaCt:settings"))
        .isTrue()
    }
  }

  @Nested
  inner class ExactMatchForNestedType {
    @Test
    fun `it should match when nested type contains the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")

      // when & then
      assertThat(descriptor.matches("exact:Provider"))
        .isTrue()
    }

    @Test
    fun `it should match when nested type contains the keyword (ignore case)`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")

      // when & then
      assertThat(descriptor.matches("exact:provider"))
        .isTrue()
    }

    @Test
    fun `it should not match when the nested type does not contain the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderRepository;")

      // when & then
      assertThat(descriptor.matches("exact:provide"))
        .isFalse()
    }
  }
}
