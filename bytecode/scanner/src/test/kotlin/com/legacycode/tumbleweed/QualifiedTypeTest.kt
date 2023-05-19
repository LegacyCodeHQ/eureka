package com.legacycode.tumbleweed

import com.google.common.truth.Truth.assertThat
import com.legacycode.tumbleweed.QualifiedType.Companion.DEFAULT_PACKAGE_NAME
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class QualifiedTypeTest {
  @Nested
  inner class SimpleName {
    @ParameterizedTest
    @ValueSource(
      strings = [
        "int",
        "long",
        "short",
        "byte",
        "boolean",
        "char",
        "float",
        "double",
        "void",
      ],
    )
    fun `return the same type for primitive types`(type: String) {
      assertThat(QualifiedType(type).simpleName)
        .isEqualTo(type)
    }

    @Test
    fun `return simple names for non-primitive types`() {
      assertThat(QualifiedType("java.lang.String").simpleName)
        .isEqualTo("String")
    }

    @Test
    fun `return simple names for primitive arrays`() {
      assertThat(QualifiedType("[]int").simpleName)
        .isEqualTo("[]int")
    }

    @Test
    fun `return simple names for non-primitive arrays`() {
      assertThat(QualifiedType("[]java.lang.String").simpleName)
        .isEqualTo("[]String")
    }

    @Test
    fun `simple names for multi-dimensional primitive arrays`() {
      assertThat(QualifiedType("[][]double").simpleName)
        .isEqualTo("[][]double")
    }

    @Test
    fun `simple names for multi-dimensional non-primitive arrays`() {
      assertThat(QualifiedType("[][]java.lang.String").simpleName)
        .isEqualTo("[][]String")
    }
  }

  @Nested
  inner class PackageName {
    @ParameterizedTest
    @ValueSource(
      strings = [
        "int",
        "long",
        "short",
        "byte",
        "boolean",
        "char",
        "float",
        "double",
        "void",
      ],
    )
    fun `default package name for primitive types`(type: String) {
      assertThat(QualifiedType(type).packageName)
        .isEqualTo(DEFAULT_PACKAGE_NAME)
    }

    @Test
    fun `package name for non-primitive types`() {
      assertThat(QualifiedType("java.lang.String").packageName)
        .isEqualTo("java.lang")
    }

    @Test
    fun `package name for primitive arrays`() {
      assertThat(QualifiedType("[]int").packageName)
        .isEqualTo(DEFAULT_PACKAGE_NAME)
    }

    @Test
    fun `package name for non-primitive arrays`() {
      assertThat(QualifiedType("[]java.lang.String").packageName)
        .isEqualTo("java.lang")
    }

    @Test
    fun `package name for multi-dimensional primitive arrays`() {
      assertThat(QualifiedType("[][]double").packageName)
        .isEqualTo(DEFAULT_PACKAGE_NAME)
    }

    @Test
    fun `package name for multi-dimensional non-primitive arrays`() {
      assertThat(QualifiedType("[][]java.lang.String").packageName)
        .isEqualTo("java.lang")
    }
  }
}
