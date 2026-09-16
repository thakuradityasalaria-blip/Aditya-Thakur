package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.Lawyer
import com.example.ui.viewmodel.LegalViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class LawyerRegistrationTest {

  private lateinit var viewModel: LegalViewModel
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    viewModel = LegalViewModel()
    viewModel.initPreferences(context)
  }

  @Test
  fun testAddCustomLawyerWithPhotoPhoneAndEmail() {
    val newLawyer = Lawyer(
      id = "test_lawyer_101",
      name = "Adv. Rehana Begum",
      designation = "High Court Matrimonial Advocate",
      courtEnrolment = "D/4512/2014",
      city = "New Delhi",
      courts = listOf("Delhi High Court", "Saket Family Court"),
      experienceYears = 9,
      languages = listOf("English", "Hindi", "Urdu"),
      specializations = listOf("Muslim Law & Khula", "Mutual Consent", "Alimony"),
      rating = 5.0,
      reviewsCount = 1,
      consultationFee = "₹ 1,500 / session",
      phone = "+91 98111 22334",
      email = "advocate.rehana@delhibar.org",
      officeAddress = "Chamber 210, Lawyers Block, High Court, New Delhi",
      aboutBio = "Specializing in matrimonial disputes, Khula, and maintenance.",
      isVerified = true,
      photoUri = "content://media/external/images/media/42"
    )

    // Add lawyer
    viewModel.addCustomLawyer(newLawyer)

    val state = viewModel.uiState.value
    assertTrue("customLawyers should contain the registered lawyer", state.customLawyers.any { it.id == newLawyer.id })
    assertEquals(newLawyer.name, state.customLawyers.first().name)
    assertEquals(newLawyer.phone, state.customLawyers.first().phone)
    assertEquals(newLawyer.email, state.customLawyers.first().email)
    assertEquals(newLawyer.photoUri, state.customLawyers.first().photoUri)

    // Newly registered lawyer should appear first in filtered lawyers
    val filtered = viewModel.filteredLawyers
    assertEquals(newLawyer.id, filtered.first().id)

    // Verify search filtering works for registered lawyer
    viewModel.setLawyerSearchQuery("Rehana")
    val searchResults = viewModel.filteredLawyers
    assertEquals(1, searchResults.size)
    assertEquals("Adv. Rehana Begum", searchResults.first().name)
  }

  @Test
  fun testLawyerDialogToggle() {
    assertFalse(viewModel.uiState.value.showAddLawyerDialog)
    viewModel.setAddLawyerDialog(true)
    assertTrue(viewModel.uiState.value.showAddLawyerDialog)
    viewModel.setAddLawyerDialog(false)
    assertFalse(viewModel.uiState.value.showAddLawyerDialog)
  }
}
