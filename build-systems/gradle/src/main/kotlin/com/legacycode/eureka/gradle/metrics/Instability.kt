package com.legacycode.eureka.gradle.metrics

import java.math.BigDecimal
import java.math.RoundingMode

data class Instability(
  val outgoing: Int,
  val incoming: Int,
) {
  val value: String
    get() {
      if (outgoing == 0 && incoming == 0) {
        return "∞"
      }

      return when (val i = outgoing.toDouble() / (incoming + outgoing)) {
        1.0 -> "1"
        0.0 -> "0"
        else -> twoDecimalPoints(i)
      }
    }

  private fun twoDecimalPoints(value: Double): String {
    return BigDecimal(value)
      .setScale(2, RoundingMode.HALF_UP)
      .toString()
  }
}
