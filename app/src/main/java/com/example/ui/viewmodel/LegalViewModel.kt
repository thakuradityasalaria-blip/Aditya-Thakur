package com.example.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.data.model.ChecklistItem
import com.example.data.model.ConsultationBooking
import com.example.data.model.DivorceGround
import com.example.data.model.GroundCategory
import com.example.data.model.Lawyer
import com.example.data.repository.LegalRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LegalUiState(
  val selectedTab: Int = 0, // 0: Grounds, 1: Find Lawyers, 2: Steps & Checklist, 3: Legal Rights & FAQs
  val groundsSearchQuery: String = "",
  val selectedGroundCategory: GroundCategory = GroundCategory.ALL,
  val selectedGround: DivorceGround? = null,
  val bookmarkedGroundIds: Set<String> = emptySet(),

  // Lawyer directory state
  val lawyerSearchQuery: String = "",
  val selectedCity: String = "All Cities",
  val selectedSpecialization: String = "All Specializations",
  val selectedLawyer: Lawyer? = null,
  val bookmarkedLawyerIds: Set<String> = emptySet(),
  val customLawyers: List<Lawyer> = emptyList(),
  val showAddLawyerDialog: Boolean = false,
  val lawyerAddedMessage: String? = null,

  // Booking consultation state
  val bookingLawyer: Lawyer? = null,
  val bookingClientName: String = "",
  val bookingClientPhone: String = "",
  val bookingCaseType: String = "Mutual Consent Divorce",
  val bookingPreferredMode: String = "Phone Call",
  val bookingNotes: String = "",
  val bookingSuccessMessage: String? = null,
  val submittedBookings: List<ConsultationBooking> = emptyList(),

  // Checklist state
  val completedChecklistIds: Set<String> = emptySet(),

  // Eligibility Advisor questionnaire
  val showAdvisorDialog: Boolean = false,
  val advisorSelectedSituation: String? = null
)

class LegalViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(LegalUiState())
  val uiState: StateFlow<LegalUiState> = _uiState.asStateFlow()

  private var prefs: SharedPreferences? = null

  private val moshi: Moshi by lazy {
    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  }

  private val lawyerListAdapter by lazy {
    moshi.adapter<List<Lawyer>>(Types.newParameterizedType(List::class.java, Lawyer::class.java))
  }

  fun initPreferences(context: Context) {
    if (prefs == null) {
      prefs = context.getSharedPreferences("bika_teen_talaaq_prefs", Context.MODE_PRIVATE)
      val completed = prefs?.getStringSet("completed_checklist", emptySet()) ?: emptySet()
      val bookmarkedGrounds = prefs?.getStringSet("bookmarked_grounds", emptySet()) ?: emptySet()
      val bookmarkedLawyers = prefs?.getStringSet("bookmarked_lawyers", emptySet()) ?: emptySet()

      val savedLawyersJson = prefs?.getString("custom_lawyers_json", null)
      val loadedCustomLawyers = if (!savedLawyersJson.isNullOrEmpty()) {
        try {
          lawyerListAdapter.fromJson(savedLawyersJson) ?: emptyList()
        } catch (e: Exception) {
          emptyList()
        }
      } else {
        emptyList()
      }

      _uiState.update {
        it.copy(
          completedChecklistIds = completed,
          bookmarkedGroundIds = bookmarkedGrounds,
          bookmarkedLawyerIds = bookmarkedLawyers,
          customLawyers = loadedCustomLawyers
        )
      }
    }
  }

  fun setTab(index: Int) {
    _uiState.update { it.copy(selectedTab = index) }
  }

  fun setGroundsSearchQuery(query: String) {
    _uiState.update { it.copy(groundsSearchQuery = query) }
  }

  fun setGroundCategory(category: GroundCategory) {
    _uiState.update { it.copy(selectedGroundCategory = category) }
  }

  fun selectGround(ground: DivorceGround?) {
    _uiState.update { it.copy(selectedGround = ground) }
  }

  fun toggleBookmarkGround(groundId: String) {
    _uiState.update { state ->
      val newSet = if (state.bookmarkedGroundIds.contains(groundId)) {
        state.bookmarkedGroundIds - groundId
      } else {
        state.bookmarkedGroundIds + groundId
      }
      prefs?.edit()?.putStringSet("bookmarked_grounds", newSet)?.apply()
      state.copy(bookmarkedGroundIds = newSet)
    }
  }

  fun setLawyerSearchQuery(query: String) {
    _uiState.update { it.copy(lawyerSearchQuery = query) }
  }

  fun setSelectedCity(city: String) {
    _uiState.update { it.copy(selectedCity = city) }
  }

  fun setSelectedSpecialization(specialization: String) {
    _uiState.update { it.copy(selectedSpecialization = specialization) }
  }

  fun selectLawyer(lawyer: Lawyer?) {
    _uiState.update { it.copy(selectedLawyer = lawyer) }
  }

  fun toggleBookmarkLawyer(lawyerId: String) {
    _uiState.update { state ->
      val newSet = if (state.bookmarkedLawyerIds.contains(lawyerId)) {
        state.bookmarkedLawyerIds - lawyerId
      } else {
        state.bookmarkedLawyerIds + lawyerId
      }
      prefs?.edit()?.putStringSet("bookmarked_lawyers", newSet)?.apply()
      state.copy(bookmarkedLawyerIds = newSet)
    }
  }

  fun openBookingDialog(lawyer: Lawyer) {
    _uiState.update {
      it.copy(
        bookingLawyer = lawyer,
        bookingClientName = "",
        bookingClientPhone = "",
        bookingCaseType = "Mutual Consent Divorce",
        bookingPreferredMode = "Phone Call",
        bookingNotes = "",
        bookingSuccessMessage = null
      )
    }
  }

  fun closeBookingDialog() {
    _uiState.update { it.copy(bookingLawyer = null, bookingSuccessMessage = null) }
  }

  fun updateBookingForm(
    name: String? = null,
    phone: String? = null,
    caseType: String? = null,
    mode: String? = null,
    notes: String? = null
  ) {
    _uiState.update { state ->
      state.copy(
        bookingClientName = name ?: state.bookingClientName,
        bookingClientPhone = phone ?: state.bookingClientPhone,
        bookingCaseType = caseType ?: state.bookingCaseType,
        bookingPreferredMode = mode ?: state.bookingPreferredMode,
        bookingNotes = notes ?: state.bookingNotes
      )
    }
  }

  fun submitBooking() {
    val state = _uiState.value
    val lawyer = state.bookingLawyer ?: return
    if (state.bookingClientName.isBlank() || state.bookingClientPhone.isBlank()) {
      return
    }

    val newBooking = ConsultationBooking(
      id = "book_${System.currentTimeMillis()}",
      lawyerId = lawyer.id,
      lawyerName = lawyer.name,
      clientName = state.bookingClientName.trim(),
      clientPhone = state.bookingClientPhone.trim(),
      caseType = state.bookingCaseType,
      preferredMode = state.bookingPreferredMode,
      notes = state.bookingNotes.trim()
    )

    _uiState.update {
      it.copy(
        submittedBookings = it.submittedBookings + newBooking,
        bookingSuccessMessage = "Consultation request sent to ${lawyer.name}! The advocate's office will reach out to you via ${state.bookingPreferredMode}."
      )
    }
  }

  fun toggleChecklistItem(itemId: String) {
    _uiState.update { state ->
      val updated = if (state.completedChecklistIds.contains(itemId)) {
        state.completedChecklistIds - itemId
      } else {
        state.completedChecklistIds + itemId
      }
      prefs?.edit()?.putStringSet("completed_checklist", updated)?.apply()
      state.copy(completedChecklistIds = updated)
    }
  }

  fun setAdvisorDialog(show: Boolean) {
    _uiState.update { it.copy(showAdvisorDialog = show, advisorSelectedSituation = null) }
  }

  fun setAddLawyerDialog(show: Boolean) {
    _uiState.update { it.copy(showAddLawyerDialog = show) }
  }

  fun dismissLawyerAddedMessage() {
    _uiState.update { it.copy(lawyerAddedMessage = null) }
  }

  fun addCustomLawyer(lawyer: Lawyer) {
    _uiState.update { state ->
      val updatedList = listOf(lawyer) + state.customLawyers
      try {
        val json = lawyerListAdapter.toJson(updatedList)
        prefs?.edit()?.putString("custom_lawyers_json", json)?.apply()
      } catch (e: Exception) {
        // Fallback gracefully
      }
      state.copy(
        customLawyers = updatedList,
        showAddLawyerDialog = false,
        selectedTab = 1,
        selectedCity = "All Cities",
        selectedSpecialization = "All Specializations",
        lawyerSearchQuery = "",
        lawyerAddedMessage = "Advocate ${lawyer.name} registered successfully! Profile is now active in directory."
      )
    }
  }

  fun applyAdvisorSituation(situation: String) {
    when (situation) {
      "mutual" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.MUTUAL,
            groundsSearchQuery = "",
            showAdvisorDialog = false
          )
        }
      }
      "triple_talaq" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.MUSLIM_LAW,
            groundsSearchQuery = "Triple Talaq",
            showAdvisorDialog = false
          )
        }
      }
      "muslim_wife" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.MUSLIM_LAW,
            groundsSearchQuery = "",
            showAdvisorDialog = false
          )
        }
      }
      "cruelty" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.HINDU_MARRIAGE,
            groundsSearchQuery = "Cruelty",
            showAdvisorDialog = false
          )
        }
      }
      "desertion" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.HINDU_MARRIAGE,
            groundsSearchQuery = "Desertion",
            showAdvisorDialog = false
          )
        }
      }
      "civil_interfaith" -> {
        _uiState.update {
          it.copy(
            selectedTab = 0,
            selectedGroundCategory = GroundCategory.SPECIAL_MARRIAGE,
            groundsSearchQuery = "",
            showAdvisorDialog = false
          )
        }
      }
    }
  }

  val filteredGrounds: List<DivorceGround>
    get() {
      val state = _uiState.value
      return LegalRepository.divorceGrounds.filter { ground ->
        val matchesCategory = (state.selectedGroundCategory == GroundCategory.ALL) ||
          (ground.category == state.selectedGroundCategory)

        val q = state.groundsSearchQuery.trim().lowercase()
        val matchesQuery = q.isEmpty() ||
          ground.title.lowercase().contains(q) ||
          ground.actAndSection.lowercase().contains(q) ||
          ground.shortDescription.lowercase().contains(q) ||
          ground.statutoryStatus.lowercase().contains(q)

        matchesCategory && matchesQuery
      }
    }

  val filteredLawyers: List<Lawyer>
    get() {
      val state = _uiState.value
      val allLawyers = state.customLawyers + LegalRepository.lawyers
      return allLawyers.filter { lawyer ->
        val matchesCity = (state.selectedCity == "All Cities") ||
          lawyer.city.equals(state.selectedCity, ignoreCase = true)

        val matchesSpecialization = (state.selectedSpecialization == "All Specializations") ||
          lawyer.specializations.any { it.contains(state.selectedSpecialization, ignoreCase = true) }

        val q = state.lawyerSearchQuery.trim().lowercase()
        val matchesQuery = q.isEmpty() ||
          lawyer.name.lowercase().contains(q) ||
          lawyer.city.lowercase().contains(q) ||
          lawyer.courtEnrolment.lowercase().contains(q) ||
          lawyer.specializations.any { it.lowercase().contains(q) } ||
          lawyer.courts.any { it.lowercase().contains(q) }

        matchesCity && matchesSpecialization && matchesQuery
      }
    }
}
