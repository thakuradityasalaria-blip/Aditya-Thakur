package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Lawyer
import com.example.ui.theme.*
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddLawyerDialog(
  onDismiss: () -> Unit,
  onLawyerAdded: (Lawyer) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var photoUriString by remember { mutableStateOf<String?>(null) }
  var isPhotoUrlInputVisible by remember { mutableStateOf(false) }
  var photoUrlInput by remember { mutableStateOf("") }

  var selectedCity by remember { mutableStateOf("New Delhi") }
  var courtEnrolment by remember { mutableStateOf("") }
  var experienceYears by remember { mutableStateOf("5") }
  var consultationFee by remember { mutableStateOf("₹ 1,500 / session") }
  var aboutBio by remember { mutableStateOf("") }
  var courtsText by remember { mutableStateOf("District Family Courts, High Court") }

  val availableSpecializations = listOf(
    "Mutual Consent",
    "Muslim Law & Khula",
    "Child Custody",
    "Alimony & Maintenance",
    "Mediation",
    "Contested Divorce",
    "Domestic Violence",
    "Stridhan Recovery"
  )
  val selectedSpecializations = remember {
    mutableStateListOf("Mutual Consent", "Muslim Law & Khula")
  }

  val availableCities = listOf(
    "New Delhi",
    "Mumbai",
    "Bengaluru",
    "Lucknow",
    "Kolkata",
    "Chandigarh",
    "Hyderabad",
    "Chennai",
    "Pune",
    "Ahmedabad",
    "Jaipur"
  )

  var nameError by remember { mutableStateOf<String?>(null) }
  var phoneError by remember { mutableStateOf<String?>(null) }
  var emailError by remember { mutableStateOf<String?>(null) }

  // Photo picker contract (Standard Android Photo Picker - zero permission)
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      photoUriString = uri.toString()
    }
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    modifier = Modifier.testTag("add_lawyer_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
        .verticalScroll(rememberScrollState())
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Advocate Onboarding",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Add yourself to bika teen talaaq.com directory",
            style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
          )
        }
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_add_lawyer_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Photo Section
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NeutralCard),
        shape = RoundedCornerShape(14.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Advocate Photo / Chamber Logo",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(12.dp))

          // Avatar Preview
          Box(
            modifier = Modifier
              .size(90.dp)
              .clip(CircleShape)
              .background(NavyLight)
              .border(3.dp, GoldAccent, CircleShape)
              .clickable {
                photoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              }
              .testTag("advocate_photo_picker_avatar"),
            contentAlignment = Alignment.Center
          ) {
            if (!photoUriString.isNullOrEmpty()) {
              AsyncImage(
                model = photoUriString,
                contentDescription = "Selected Advocate Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            } else {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                  imageVector = Icons.Default.AddAPhoto,
                  contentDescription = "Upload Photo",
                  tint = PureWhite,
                  modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "Add Photo",
                  fontSize = 11.sp,
                  color = PureWhite,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = {
                photoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_select_photo")
            ) {
              Icon(imageVector = Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Choose Photo", fontSize = 12.sp)
            }

            OutlinedButton(
              onClick = { isPhotoUrlInputVisible = !isPhotoUrlInputVisible },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_toggle_photo_url")
            ) {
              Icon(imageVector = Icons.Default.Link, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Paste URL", fontSize = 12.sp)
            }

            if (photoUriString != null) {
              IconButton(
                onClick = { photoUriString = null }
              ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Photo", tint = LegalRed)
              }
            }
          }

          if (isPhotoUrlInputVisible) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
              value = photoUrlInput,
              onValueChange = {
                photoUrlInput = it
                if (it.isNotBlank()) {
                  photoUriString = it.trim()
                }
              },
              label = { Text("Photo Web URL (e.g. https://...)") },
              placeholder = { Text("https://example.com/photo.jpg") },
              singleLine = true,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("photo_url_input")
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Name Input
      OutlinedTextField(
        value = name,
        onValueChange = {
          name = it
          if (it.isNotBlank()) nameError = null
        },
        label = { Text("Advocate Full Name *") },
        placeholder = { Text("e.g. Adv. Rajesh Sharma or Dr. Priya Mehta") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = NavyPrimary) },
        isError = nameError != null,
        supportingText = {
          nameError?.let { Text(it, color = LegalRed) }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_name")
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Phone Input
      OutlinedTextField(
        value = phone,
        onValueChange = {
          phone = it
          if (it.isNotBlank()) phoneError = null
        },
        label = { Text("Phone Number / Chamber Hotline *") },
        placeholder = { Text("e.g. +91 98101 23456") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = NavyPrimary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        isError = phoneError != null,
        supportingText = {
          phoneError?.let { Text(it, color = LegalRed) }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_phone")
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Email Input
      OutlinedTextField(
        value = email,
        onValueChange = {
          email = it
          if (it.isNotBlank()) emailError = null
        },
        label = { Text("Email Address *") },
        placeholder = { Text("e.g. advocate.rajesh@lawchambers.in") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = NavyPrimary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = emailError != null,
        supportingText = {
          emailError?.let { Text(it, color = LegalRed) }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_email")
      )

      Spacer(modifier = Modifier.height(12.dp))

      // City / Jurisdiction
      Text(
        text = "Primary City / Jurisdiction",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        availableCities.forEach { city ->
          val isSelected = selectedCity == city
          FilterChip(
            selected = isSelected,
            onClick = { selectedCity = city },
            label = { Text(city, fontSize = 12.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = NavyPrimary,
              selectedLabelColor = PureWhite
            ),
            modifier = Modifier.testTag("city_select_$city")
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Bar Enrolment
      OutlinedTextField(
        value = courtEnrolment,
        onValueChange = { courtEnrolment = it },
        label = { Text("Bar Council Enrolment No.") },
        placeholder = { Text("e.g. D/1842/2015 or MAH/552/2012") },
        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = SlateSecondary) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_enrolment")
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Courts
      OutlinedTextField(
        value = courtsText,
        onValueChange = { courtsText = it },
        label = { Text("Courts of Regular Practice") },
        placeholder = { Text("e.g. District Courts, High Court, Supreme Court") },
        leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null, tint = SlateSecondary) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_courts")
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Experience & Fee
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = experienceYears,
          onValueChange = { experienceYears = it },
          label = { Text("Experience (Years)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("lawyer_input_experience")
        )

        OutlinedTextField(
          value = consultationFee,
          onValueChange = { consultationFee = it },
          label = { Text("Consultation Fee") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("lawyer_input_fee")
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Specializations Chips
      Text(
        text = "Practice Specializations",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(6.dp))
      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        availableSpecializations.forEach { spec ->
          val isSelected = selectedSpecializations.contains(spec)
          FilterChip(
            selected = isSelected,
            onClick = {
              if (isSelected) {
                if (selectedSpecializations.size > 1) {
                  selectedSpecializations.remove(spec)
                }
              } else {
                selectedSpecializations.add(spec)
              }
            },
            label = { Text(spec, fontSize = 11.sp) },
            leadingIcon = if (isSelected) {
              { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
            } else null,
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = GoldDark,
              selectedLabelColor = PureWhite
            ),
            modifier = Modifier.testTag("spec_toggle_$spec")
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Bio / About
      OutlinedTextField(
        value = aboutBio,
        onValueChange = { aboutBio = it },
        label = { Text("Chamber Overview / Bio") },
        placeholder = { Text("Describe your practice experience, notable cases, or mediation approach...") },
        minLines = 3,
        maxLines = 5,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_input_bio")
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Submit Button
      Button(
        onClick = {
          var hasError = false
          if (name.trim().isEmpty()) {
            nameError = "Advocate name is required"
            hasError = true
          }
          if (phone.trim().isEmpty()) {
            phoneError = "Phone number is required"
            hasError = true
          }
          if (email.trim().isEmpty()) {
            emailError = "Email address is required"
            hasError = true
          } else if (!email.contains("@") || !email.contains(".")) {
            emailError = "Please enter a valid email address"
            hasError = true
          }

          if (!hasError) {
            val exp = experienceYears.toIntOrNull() ?: 3
            val courtsList = courtsText.split(",")
              .map { it.trim() }
              .filter { it.isNotEmpty() }
              .ifEmpty { listOf("Family Courts") }

            val cleanBio = if (aboutBio.trim().isNotEmpty()) {
              aboutBio.trim()
            } else {
              "Practicing advocate in $selectedCity focusing on ${selectedSpecializations.joinToString(", ")}."
            }

            val newLawyer = Lawyer(
              id = "lawyer_custom_${UUID.randomUUID().toString().take(8)}",
              name = if (name.trim().startsWith("Adv", ignoreCase = true)) name.trim() else "Adv. ${name.trim()}",
              designation = "Advocate & Family Law Practitioner",
              courtEnrolment = if (courtEnrolment.trim().isNotEmpty()) courtEnrolment.trim() else "Bar Council of ${selectedCity}",
              city = selectedCity,
              courts = courtsList,
              experienceYears = exp,
              languages = listOf("English", "Hindi"),
              specializations = selectedSpecializations.toList(),
              rating = 5.0,
              reviewsCount = 1,
              consultationFee = consultationFee.ifBlank { "₹ 1,500 / session" },
              phone = phone.trim(),
              email = email.trim(),
              officeAddress = "Chamber at $selectedCity Court Complex",
              aboutBio = cleanBio,
              isVerified = true,
              consultationModes = listOf("In-Person", "Phone Call", "Video Consultation"),
              photoUri = photoUriString
            )

            onLawyerAdded(newLawyer)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("btn_submit_lawyer_registration")
      ) {
        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GoldAccent)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Publish Profile to Directory",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = PureWhite
        )
      }
    }
  }
}
