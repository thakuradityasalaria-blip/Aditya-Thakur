package com.example.data.model

enum class GroundCategory(val displayName: String) {
  ALL("All Grounds"),
  MUTUAL("Mutual Consent"),
  MUSLIM_LAW("Muslim Law & Talaq"),
  HINDU_MARRIAGE("Hindu Marriage Act"),
  SPECIAL_MARRIAGE("Special / Civil Marriage"),
  OTHER_LAWS("Christian & Parsi Laws")
}

data class DivorceGround(
  val id: String,
  val title: String,
  val category: GroundCategory,
  val actAndSection: String,
  val shortDescription: String,
  val detailedExplanation: String,
  val keyRequirements: List<String>,
  val requiredEvidence: List<String>,
  val statutoryStatus: String,
  val isIllegalWarning: Boolean = false,
  val warningNote: String? = null,
  val practicalTips: String
)
