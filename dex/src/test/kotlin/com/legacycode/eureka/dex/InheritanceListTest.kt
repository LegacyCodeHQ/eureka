package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class InheritanceListTest {
  private val inheritanceList = InheritanceList()

  @Test
  fun `it can create an empty inheritance list`() {
    // when & then
    assertThat(inheritanceList.isEmpty)
      .isTrue()
  }

  @Test
  fun `it can get the ancestor`() {
    // when
    inheritanceList.add("Lcom/legacycode/dex/Child", "Ljava/lang/Object;")

    // then
    assertThat(inheritanceList.ancestors())
      .containsExactly("Ljava/lang/Object;")
  }

  @Test
  fun `it can get the children`() {
    // when
    with(inheritanceList) {
      add("Lcom/legacycode/dex/Child", "Ljava/lang/Object;")
      add("Lcom/legacycode/dex/Node", "Ljava/lang/Object;")
    }

    // then
    assertThat(inheritanceList.children("Ljava/lang/Object;"))
      .containsExactly(
        "Lcom/legacycode/dex/Child",
        "Lcom/legacycode/dex/Node",
      )
  }
}
