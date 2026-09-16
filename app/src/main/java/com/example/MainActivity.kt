package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddLawyerDialog
import com.example.ui.components.BookingDialog
import com.example.ui.components.GroundDetailSheet
import com.example.ui.components.LawyerDetailSheet
import com.example.ui.components.SituationAdvisorDialog
import com.example.ui.screens.GroundsScreen
import com.example.ui.screens.LawyersScreen
import com.example.ui.screens.ProcedureScreen
import com.example.ui.screens.RightsAndFaqScreen
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.viewmodel.LegalViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        BikaTeenTalaaqApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikaTeenTalaaqApp(
  viewModel: LegalViewModel = viewModel()
) {
  val context = LocalContext.current
  LaunchedEffect(Unit) {
    viewModel.initPreferences(context)
  }

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "bika teen talaaq.com",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = PureWhite,
                fontSize = 19.sp
              )
            )
            Text(
              text = "Divorce Grounds & Matrimonial Advocate Directory",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFFD3E0EA),
                fontSize = 11.sp
              )
            )
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.setAddLawyerDialog(true) },
            modifier = Modifier.testTag("topbar_add_lawyer_button")
          ) {
            Icon(
              imageVector = Icons.Default.PersonAdd,
              contentDescription = "Join as Advocate",
              tint = GoldAccent
            )
          }
          IconButton(
            onClick = { viewModel.setAdvisorDialog(true) },
            modifier = Modifier.testTag("topbar_advisor_button")
          ) {
            Icon(
              imageVector = Icons.Default.HelpOutline,
              contentDescription = "Case Advisor",
              tint = GoldAccent
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = NavyPrimary,
          titleContentColor = PureWhite,
          actionIconContentColor = GoldAccent
        ),
        modifier = Modifier.testTag("app_top_bar")
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
      ) {
        NavigationBarItem(
          icon = { Icon(Icons.Default.Gavel, contentDescription = "Grounds") },
          label = { Text("Grounds", fontSize = 11.sp) },
          selected = uiState.selectedTab == 0,
          onClick = { viewModel.setTab(0) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavyPrimary,
            selectedTextColor = NavyPrimary,
            indicatorColor = Color(0xFFE8F0FE)
          ),
          modifier = Modifier.testTag("nav_item_grounds")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.PersonSearch, contentDescription = "Find Lawyers") },
          label = { Text("Find Lawyers", fontSize = 11.sp) },
          selected = uiState.selectedTab == 1,
          onClick = { viewModel.setTab(1) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavyPrimary,
            selectedTextColor = NavyPrimary,
            indicatorColor = Color(0xFFE8F0FE)
          ),
          modifier = Modifier.testTag("nav_item_lawyers")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.Assignment, contentDescription = "Procedure") },
          label = { Text("Procedure", fontSize = 11.sp) },
          selected = uiState.selectedTab == 2,
          onClick = { viewModel.setTab(2) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavyPrimary,
            selectedTextColor = NavyPrimary,
            indicatorColor = Color(0xFFE8F0FE)
          ),
          modifier = Modifier.testTag("nav_item_procedure")
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.MenuBook, contentDescription = "Rights & FAQ") },
          label = { Text("Rights & FAQ", fontSize = 11.sp) },
          selected = uiState.selectedTab == 3,
          onClick = { viewModel.setTab(3) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavyPrimary,
            selectedTextColor = NavyPrimary,
            indicatorColor = Color(0xFFE8F0FE)
          ),
          modifier = Modifier.testTag("nav_item_rights")
        )
      }
    }
  ) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      when (uiState.selectedTab) {
        0 -> GroundsScreen(
          grounds = viewModel.filteredGrounds,
          searchQuery = uiState.groundsSearchQuery,
          selectedCategory = uiState.selectedGroundCategory,
          bookmarkedGroundIds = uiState.bookmarkedGroundIds,
          onSearchQueryChange = { viewModel.setGroundsSearchQuery(it) },
          onCategorySelect = { viewModel.setGroundCategory(it) },
          onGroundClick = { viewModel.selectGround(it) },
          onBookmarkToggle = { viewModel.toggleBookmarkGround(it) },
          onOpenAdvisor = { viewModel.setAdvisorDialog(true) }
        )
        1 -> LawyersScreen(
          lawyers = viewModel.filteredLawyers,
          searchQuery = uiState.lawyerSearchQuery,
          selectedCity = uiState.selectedCity,
          selectedSpecialization = uiState.selectedSpecialization,
          bookmarkedLawyerIds = uiState.bookmarkedLawyerIds,
          lawyerAddedMessage = uiState.lawyerAddedMessage,
          onDismissLawyerAddedMessage = { viewModel.dismissLawyerAddedMessage() },
          onAddLawyerClick = { viewModel.setAddLawyerDialog(true) },
          onSearchQueryChange = { viewModel.setLawyerSearchQuery(it) },
          onCitySelect = { viewModel.setSelectedCity(it) },
          onSpecializationSelect = { viewModel.setSelectedSpecialization(it) },
          onLawyerClick = { viewModel.selectLawyer(it) },
          onBookClick = { viewModel.openBookingDialog(it) },
          onBookmarkToggle = { viewModel.toggleBookmarkLawyer(it) }
        )
        2 -> ProcedureScreen(
          completedChecklistIds = uiState.completedChecklistIds,
          onToggleChecklistItem = { viewModel.toggleChecklistItem(it) }
        )
        3 -> RightsAndFaqScreen()
      }
    }
  }

  // Ground Detail Sheet
  uiState.selectedGround?.let { ground ->
    GroundDetailSheet(
      ground = ground,
      onDismiss = { viewModel.selectGround(null) },
      onFindLawyersClick = {
        viewModel.selectGround(null)
        // Auto-filter lawyers for this ground
        val relevantSpec = when (ground.category) {
          com.example.data.model.GroundCategory.MUTUAL -> "Mutual Consent"
          com.example.data.model.GroundCategory.MUSLIM_LAW -> "Muslim Law"
          else -> "Contested Divorce"
        }
        viewModel.setSelectedSpecialization(relevantSpec)
        viewModel.setTab(1)
      }
    )
  }

  // Lawyer Detail Sheet
  uiState.selectedLawyer?.let { lawyer ->
    LawyerDetailSheet(
      lawyer = lawyer,
      onDismiss = { viewModel.selectLawyer(null) },
      onBookClick = {
        viewModel.selectLawyer(null)
        viewModel.openBookingDialog(lawyer)
      }
    )
  }

  // Consultation Booking Dialog
  uiState.bookingLawyer?.let { lawyer ->
    BookingDialog(
      lawyer = lawyer,
      clientName = uiState.bookingClientName,
      clientPhone = uiState.bookingClientPhone,
      caseType = uiState.bookingCaseType,
      preferredMode = uiState.bookingPreferredMode,
      notes = uiState.bookingNotes,
      successMessage = uiState.bookingSuccessMessage,
      onNameChange = { viewModel.updateBookingForm(name = it) },
      onPhoneChange = { viewModel.updateBookingForm(phone = it) },
      onCaseTypeChange = { viewModel.updateBookingForm(caseType = it) },
      onModeChange = { viewModel.updateBookingForm(mode = it) },
      onNotesChange = { viewModel.updateBookingForm(notes = it) },
      onSubmit = { viewModel.submitBooking() },
      onDismiss = { viewModel.closeBookingDialog() }
    )
  }

  // Situation Advisor Dialog
  if (uiState.showAdvisorDialog) {
    SituationAdvisorDialog(
      onDismiss = { viewModel.setAdvisorDialog(false) },
      onSelectSituation = { viewModel.applyAdvisorSituation(it) }
    )
  }

  // Add Lawyer Dialog
  if (uiState.showAddLawyerDialog) {
    AddLawyerDialog(
      onDismiss = { viewModel.setAddLawyerDialog(false) },
      onLawyerAdded = { newLawyer ->
        viewModel.addCustomLawyer(newLawyer)
      }
    )
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

