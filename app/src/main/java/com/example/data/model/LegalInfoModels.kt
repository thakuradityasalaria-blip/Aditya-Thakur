package com.example.data.model

data class ChecklistItem(
  val id: String,
  val title: String,
  val description: String,
  val category: String, // "Identity & Marriage", "Financial & Assets", "Evidence & Pleadings"
  val isMandatory: Boolean,
  val isCompleted: Boolean = false,
  val tips: String
)

data class ProcedureStep(
  val stepNumber: Int,
  val title: String,
  val timeEstimate: String,
  val description: String,
  val keyActions: List<String>,
  val statutoryNote: String
)

data class LegalFaq(
  val id: String,
  val question: String,
  val answer: String,
  val tag: String
)
