package com.example.data.model

data class Lawyer(
  val id: String,
  val name: String,
  val designation: String,
  val courtEnrolment: String,
  val city: String,
  val courts: List<String>,
  val experienceYears: Int,
  val languages: List<String>,
  val specializations: List<String>,
  val rating: Double,
  val reviewsCount: Int,
  val consultationFee: String,
  val phone: String,
  val email: String,
  val officeAddress: String,
  val aboutBio: String,
  val isVerified: Boolean = true,
  val consultationModes: List<String> = listOf("In-Person", "Phone Call", "Video Consultation"),
  val photoUri: String? = null
)

data class ConsultationBooking(
  val id: String,
  val lawyerId: String,
  val lawyerName: String,
  val clientName: String,
  val clientPhone: String,
  val caseType: String,
  val preferredMode: String,
  val notes: String,
  val timestampMillis: Long = System.currentTimeMillis()
)
