package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DivorceGround
import com.example.ui.theme.*

@Composable
fun GroundCard(
  ground: DivorceGround,
  isBookmarked: Boolean,
  onBookmarkClick: () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("ground_card_${ground.id}")
      .clickable { onClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
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
        // Tag badge
        val (badgeBg, badgeTextColor) = when {
          ground.isIllegalWarning -> Color(0xFFFFEBEE) to LegalRed
          ground.id == "mutual_consent" -> Color(0xFFE8F5E9) to LegalGreen
          else -> Color(0xFFE3F2FD) to LegalBlue
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(badgeBg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            if (ground.isIllegalWarning) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = LegalRed,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
            } else {
              Icon(
                imageVector = Icons.Default.Gavel,
                contentDescription = null,
                tint = badgeTextColor,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
              text = ground.statutoryStatus,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = badgeTextColor
            )
          }
        }

        IconButton(
          onClick = onBookmarkClick,
          modifier = Modifier
            .size(32.dp)
            .testTag("bookmark_ground_${ground.id}")
        ) {
          Icon(
            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (isBookmarked) "Remove bookmark" else "Bookmark ground",
            tint = if (isBookmarked) GoldAccent else SlateSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = ground.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Text(
        text = ground.actAndSection,
        style = MaterialTheme.typography.bodySmall.copy(
          color = GoldDark,
          fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
      )

      Text(
        text = ground.shortDescription,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp
        ),
        maxLines = 3
      )

      if (ground.isIllegalWarning) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFFFF3E0))
            .padding(8.dp)
        ) {
          Text(
            text = "Criminal prohibition under 2019 Act. Instant talaq is legally void.",
            fontSize = 11.sp,
            color = LegalAmber,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${ground.requiredEvidence.size} Key Evidence Items Required",
          style = MaterialTheme.typography.labelSmall.copy(color = SlateSecondary)
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { onClick() }
        ) {
          Text(
            text = "View Details",
            style = MaterialTheme.typography.labelMedium.copy(
              color = NavyPrimary,
              fontWeight = FontWeight.Bold
            )
          )
          Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = NavyPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
