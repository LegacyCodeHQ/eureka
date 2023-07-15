package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SearchPolicyTest {
  @Nested
  inner class Default {
    @Test
    fun `it should not match when the type does not have the search term`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")
      val searchPolicy = SearchPolicy.from("Celebrate")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }

    @Test
    fun `it should match when type name contains the search term`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")
      val searchPolicy = SearchPolicy.from("Help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should match when type name contains the keyword (ignore case)`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragmentL;")
      val searchPolicy = SearchPolicy.from("help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should not match when the package name contains the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg/wikipedia/settings/languages/WikipediaLanguagesActivity;")
      val searchPolicy = SearchPolicy.from("settings")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }
  }

  @Nested
  inner class DefaultForNestedType {
    @Test
    fun `it should match when nested type contains the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")
      val searchPolicy = SearchPolicy.from("Help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should match when nested type contains the keyword (ignore case)`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")
      val searchPolicy = SearchPolicy.from("help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should not match when the nested type does not contain the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderRepository;")
      val searchPolicy = SearchPolicy.from("help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }
  }

  @Nested
  inner class Exact {
    @Test
    fun `it should match when type name contains the exact terminology`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("exact:help")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should not match if the type name does not contain the exact terminology 'exact' prefix`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("exact:set")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }

    @Test
    fun `it should match if the type name contains the exact terminology when using 'exact' prefix`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("exact:settings")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should ignore case in the 'exact' directive`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("eXaCt:settings")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }
  }

  @Nested
  inner class ExactMatchForNestedType {
    @Test
    fun `it should match when nested type contains the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")
      val searchPolicy = SearchPolicy.from("exact:Provider")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should match when nested type contains the keyword (ignore case)`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderHelper;")
      val searchPolicy = SearchPolicy.from("exact:provider")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should not match when the nested type does not contain the keyword`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment${'$'}NodeProviderRepository;")
      val searchPolicy = SearchPolicy.from("exact:provide")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }
  }

  @Nested
  inner class Regex {
    @Test
    fun `it should match when type matches regex`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("regex:S.+s")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }

    @Test
    fun `it should not match if the type name does not match the regex`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("regex:A.+")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isFalse()
    }

    @Test
    fun `it should ignore case in the 'regex' directive`() {
      // given
      val descriptor = Descriptor.from("Lorg.thought.crime.secure.HelpSettingsFragment;")
      val searchPolicy = SearchPolicy.from("rEgeX:S.+s")

      // when & then
      assertThat(searchPolicy.matches(descriptor.simpleClassName))
        .isTrue()
    }
  }
}
