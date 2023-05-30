package com.legacycode.eureka.gradle.metrics

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

data class Instability(
  val outgoing: Int,
  val incoming: Int,
) {
  companion object {
    const val MAXIMALLY_STABLE = "0"
    const val MAXIMALLY_UNSTABLE = "1"
    const val UNUSED = "∞"

    private const val DECIMAL_PLACES_TO_SHOW = 2
  }

  val value: String
    get() {
      if (outgoing == 0 && incoming == 0) {
        return UNUSED
      }

      return when (val i = outgoing.toDouble() / (incoming + outgoing)) {
        1.0 -> MAXIMALLY_UNSTABLE
        0.0 -> MAXIMALLY_STABLE
        else -> twoDecimalPoints(i)
      }
    }

  private fun twoDecimalPoints(value: Double): String {
    return BigDecimal(value)
      .setScale(DECIMAL_PLACES_TO_SHOW, HALF_UP)
      .toString()
  }
}
