package com.legacycode.eureka.gradle.metrics

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

data class Instability(
  val fanOut: Int,
  val fanIn: Int,
) {
  companion object {
    const val MAXIMALLY_STABLE = "0"
    const val MAXIMALLY_UNSTABLE = "1"
    const val INDEPENDENT = "∞"

    private const val DECIMAL_PLACES_TO_SHOW = 2
  }

  val value: String
    get() {
      if (fanOut == 0 && fanIn == 0) {
        return INDEPENDENT
      }

      return when (val i = fanOut.toDouble() / (fanIn + fanOut)) {
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
