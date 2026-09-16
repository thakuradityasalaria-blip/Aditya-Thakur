package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lawyer
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDialog(
  lawyer: Lawyer,
  clientName: String,
  clientPhone: String,
  caseType: String,
  preferredMode: String,
  notes: String,
  successMessage: String?,
  onNameChange: (String) -> Unit,
  onPhoneChange: (String) -> Unit,
  onCaseTypeChange: (String) -> Unit,
  onModeChange: (String) -> Unit,
  onNotesChange: (String) -> Unit,
  onSubmit: () -> Unit,
  onDismiss: () -> Unit
) {
  val caseTypes = listOf(
    "Mutual Consent Divorce",
    "Contested Divorce (Cruelty/Desertion)",
    "Triple Talaq / Khula / Muslim Law",
    "Child Custody & Visitation",
    "Alimony & Maintenance Settlement",
    "Pre-Litigation Mediation"
  )

  val modes = listOf("Phone Call", "Video Consultation", "In-Person Chamber")

  var expandedCaseType by remember { mutableStateOf(false) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    modifier = Modifier.testTag("booking_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Request Legal Consultation",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "With ${lawyer.name}",
            style = MaterialTheme.typography.bodySmall.copy(color = GoldDark, fontWeight = FontWeight.SemiBold)
          )
        }
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_booking_sheet")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      if (successMessage != null) {
        Card(
          colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = LegalGreen,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Request Successfully Submitted",
                fontWeight = FontWeight.Bold,
                color = LegalGreen,
                fontSize = 15.sp
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = successMessage,
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = NavyPrimary)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
              onClick = onDismiss,
              modifier = Modifier.fillMaxWidth(),
              colors = ButtonDefaults.buttonColors(containerColor = LegalGreen)
            ) {
              Text("Done", color = PureWhite)
            }
          }
        }
      } else {
        // Form
        Card(
          colors = CardDefaults.cardColors(containerColor = OffWhiteSurface),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = LegalBlue,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Confidential Inquiry • Protected by Advocate-Client Privilege",
              style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = clientName,
          onValueChange = onNameChange,
          label = { Text("Your Full Name") },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_client_name_input"),
          leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = SlateSecondary)
          }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = clientPhone,
          onValueChange = onPhoneChange,
          label = { Text("Mobile Number (for Callback)") },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_client_phone_input"),
          leadingIcon = {
            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = SlateSecondary)
          }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Select Matter Type",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))

        ExposedDropdownMenuBox(
          expanded = expandedCaseType,
          onExpandedChange = { expandedCaseType = it }
        ) {
          OutlinedTextField(
            value = caseType,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCaseType) },
            modifier = Modifier
              .menuAnchor()
              .fillMaxWidth()
              .testTag("booking_casetype_dropdown")
          )
          ExposedDropdownMenu(
            expanded = expandedCaseType,
            onDismissRequest = { expandedCaseType = false }
          ) {
            caseTypes.forEach { type ->
              DropdownMenuItem(
                text = { Text(type) },
                onClick = {
                  onCaseTypeChange(type)
                  expandedCaseType = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Preferred Consultation Mode",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          modes.forEach { mode ->
            val isSelected = (mode == preferredMode)
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) NavyPrimary else NeutralCard)
                .clickable { onModeChange(mode) }
                .padding(vertical = 10.dp, horizontal = 4.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = mode,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = notes,
          onValueChange = onNotesChange,
          label = { Text("Brief Case Summary / Question (Optional)") },
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .testTag("booking_notes_input"),
          maxLines = 4
        )

        Spacer(modifier = Modifier.height(20.dp))

        val isFormValid = clientName.isNotBlank() && clientPhone.isNotBlank()

        Button(
          onClick = onSubmit,
          enabled = isFormValid,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("submit_booking_button"),
          colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Submit Consultation Request",
            fontWeight = FontWeight.Bold,
            color = PureWhite
          )
        }
      }
    }
  }
}
