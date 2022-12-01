package io.redgreen.tumbleweed.vcs.blame.observablehq

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.redgreen.tumbleweed.vcs.blame.BlameResult

data class Ownership(
  val name: String,
  val size: Int,
  val percentage: Double,
)

data class OwnershipTreemap(
  val name: String,
  val children: List<Ownership>,
) {
  companion object {
    fun from(blameResult: BlameResult): OwnershipTreemap {
      val ownershipByEmail = blameResult.byEmail()
      val ownerships = ownershipByEmail.map { (email, blameLines) ->
        val percentage = calculatePercentage(blameLines.size, blameResult.lines.size)
        Ownership(email.address, blameLines.size, percentage)
      }
      return OwnershipTreemap(blameResult.repoFile.name, ownerships)
    }

    private fun calculatePercentage(
      linesByOwner: Int,
      totalLines: Int,
    ): Double {
      val percentage = linesByOwner / totalLines.toDouble() * 100
      return String.format("%.2f", percentage).toDouble()
    }
  }

  fun toJson(): String {
    return jacksonObjectMapper()
      .registerModule(kotlinModule())
      .writeValueAsString(this)
  }
}
