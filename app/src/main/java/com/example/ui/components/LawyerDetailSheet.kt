package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Lawyer
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawyerDetailSheet(
  lawyer: Lawyer,
  onDismiss: () -> Unit,
  onBookClick: () -> Unit
) {
  val context = LocalContext.current

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    modifier = Modifier.testTag("lawyer_detail_sheet")
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
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(NavyPrimary),
            contentAlignment = Alignment.Center
          ) {
            if (!lawyer.photoUri.isNullOrEmpty()) {
              AsyncImage(
                model = lawyer.photoUri,
                contentDescription = lawyer.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            } else {
              val initials = lawyer.name.split(" ")
                .filter { it.isNotBlank() && !it.startsWith("Adv", ignoreCase = true) }
                .take(2)
                .mapNotNull { it.firstOrNull()?.toString() }
                .joinToString("")
              Text(
                text = if (initials.isNotEmpty()) initials else "AD",
                color = PureWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
              )
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = lawyer.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
              )
              if (lawyer.isVerified) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.Verified,
                  contentDescription = "Verified",
                  tint = LegalBlue,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
            Text(
              text = lawyer.designation,
              style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
            )
          }
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_lawyer_sheet_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Enrolment & Experience Card
      Card(
        colors = CardDefaults.cardColors(containerColor = NeutralCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "Bar Council Enrolment:",
            style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
          )
          Text(
            text = lawyer.courtEnrolment,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = NavyPrimary
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = "Experience",
                style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
              )
              Text(
                text = "${lawyer.experienceYears} Years",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
            }
            Column {
              Text(
                text = "Client Rating",
                style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
              )
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = null,
                  tint = GoldAccent,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = "${lawyer.rating} (${lawyer.reviewsCount} reviews)",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }
            }
            Column {
              Text(
                text = "Consultation",
                style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
              )
              Text(
                text = lawyer.consultationFee,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = LegalGreen
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      Text(
        text = "About the Advocate",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = lawyer.aboutBio,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 22.sp
        )
      )

      Spacer(modifier = Modifier.height(18.dp))

      Text(
        text = "Key Practice Areas & Specializations",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(8.dp))
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        lawyer.specializations.forEach { spec ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = spec,
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      Text(
        text = "Primary Court Jurisdictions",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = lawyer.courts.joinToString(" • "),
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Languages: ${lawyer.languages.joinToString(", ")}",
        style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
      )

      Spacer(modifier = Modifier.height(18.dp))

      // Office Address card
      Card(
        colors = CardDefaults.cardColors(containerColor = OffWhiteSurface),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = LegalAmber,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = lawyer.officeAddress,
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
              data = Uri.parse("tel:${lawyer.phone}")
            }
            context.startActivity(intent)
          },
          modifier = Modifier
            .weight(1f)
            .height(48.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Call Now")
        }

        OutlinedButton(
          onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
              data = Uri.parse("mailto:${lawyer.email}")
              putExtra(Intent.EXTRA_SUBJECT, "Matrimonial Legal Consultation Inquiry - via bika teen talaaq.com")
            }
            context.startActivity(intent)
          },
          modifier = Modifier
            .weight(1f)
            .height(48.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Email")
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = onBookClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("book_from_detail_sheet_button"),
        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = PureWhite)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Book Confidential Consultation",
          fontWeight = FontWeight.Bold,
          color = PureWhite
        )
      }
    }
  }
}
