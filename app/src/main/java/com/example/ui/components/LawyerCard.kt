package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Lawyer
import com.example.ui.theme.*

@Composable
fun LawyerCard(
  lawyer: Lawyer,
  isBookmarked: Boolean,
  onBookmarkClick: () -> Unit,
  onCardClick: () -> Unit,
  onBookClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("lawyer_card_${lawyer.id}")
      .clickable { onCardClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(NavyLight),
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
                fontSize = 16.sp
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = lawyer.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              if (lawyer.isVerified) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.Verified,
                  contentDescription = "Verified Advocate",
                  tint = LegalBlue,
                  modifier = Modifier.size(16.dp)
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
          onClick = onBookmarkClick,
          modifier = Modifier
            .size(32.dp)
            .testTag("bookmark_lawyer_${lawyer.id}")
        ) {
          Icon(
            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (isBookmarked) "Saved" else "Save Lawyer",
            tint = if (isBookmarked) GoldAccent else SlateSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Location, Court, Experience row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = LegalAmber,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = lawyer.city,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
          )
          Text(
            text = " • ${lawyer.experienceYears} Yrs Exp",
            style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = GoldAccent,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = "${lawyer.rating}",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = " (${lawyer.reviewsCount})",
            fontSize = 11.sp,
            color = SlateSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Specialization chips
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        lawyer.specializations.take(3).forEach { spec ->
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(NeutralCard)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = spec,
              fontSize = 11.sp,
              color = NavyPrimary,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Divider(color = BorderSubtle, thickness = 0.8.dp)

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Consultation Fee",
            style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
          )
          Text(
            text = lawyer.consultationFee,
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = LegalGreen
            )
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedButton(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${lawyer.phone}")
              }
              context.startActivity(intent)
            },
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier
              .height(36.dp)
              .testTag("call_lawyer_${lawyer.id}")
          ) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = "Call",
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Call", fontSize = 12.sp)
          }

          Button(
            onClick = onBookClick,
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier
              .height(36.dp)
              .testTag("book_consultation_${lawyer.id}")
          ) {
            Icon(
              imageVector = Icons.Default.CalendarMonth,
              contentDescription = "Book",
              modifier = Modifier.size(14.dp),
              tint = PureWhite
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Consult", fontSize = 12.sp, color = PureWhite)
          }
        }
      }
    }
  }
}
