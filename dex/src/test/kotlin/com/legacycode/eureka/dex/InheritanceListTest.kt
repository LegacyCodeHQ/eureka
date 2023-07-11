package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class InheritanceListTest {
  private val inheritanceList = InheritanceList()

  @Test
  fun `create an empty inheritance list`() {
    // when & then
    assertThat(inheritanceList.isEmpty)
      .isTrue()
  }

  @Test
  fun `get ancestor`() {
    // when
    inheritanceList.add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child"))

    // then
    assertThat(inheritanceList.ancestors())
      .containsExactly("Ljava/lang/Object;")
  }

  @Test
  fun `get children`() {
    // when
    with(inheritanceList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node"))
    }

    // then
    assertThat(inheritanceList.children("Ljava/lang/Object;"))
      .containsExactly(
        "Lcom/legacycode/dex/Child",
        "Lcom/legacycode/dex/Node",
      )
  }

  @Test
  fun `return an empty set when parent does not exist`() {
    // when
    with(inheritanceList) {
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Child"))
      add(Ancestor("Ljava/lang/Object;"), Child("Lcom/legacycode/dex/Node"))
    }

    // then
    assertThat(inheritanceList.children("Landroid/app/Activity;"))
      .isEmpty()
  }
}
