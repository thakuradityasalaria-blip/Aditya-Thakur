package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.PersonSearch
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroundDetailSheet(
  ground: DivorceGround,
  onDismiss: () -> Unit,
  onFindLawyersClick: () -> Unit
) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    modifier = Modifier.testTag("ground_detail_sheet")
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
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (ground.isIllegalWarning) Color(0xFFFFEBEE) else Color(0xFFE8F0FE))
            .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
          Text(
            text = ground.statutoryStatus,
            color = if (ground.isIllegalWarning) LegalRed else NavyPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("close_ground_detail_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = ground.title,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Text(
        text = ground.actAndSection,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = GoldDark,
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
      )

      if (ground.warningNote != null) {
        Card(
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD)),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "Warning",
              tint = LegalRed,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = ground.warningNote,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF856404),
                lineHeight = 18.sp
              )
            )
          }
        }
      }

      Text(
        text = "Legal Framework & Meaning",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = ground.detailedExplanation,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 22.sp
        )
      )

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Essential Legal Conditions",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(8.dp))
      ground.keyRequirements.forEach { req ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.Top
        ) {
          Box(
            modifier = Modifier
              .padding(top = 4.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(GoldAccent)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = req,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurface,
              lineHeight = 20.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Evidence & Documents Required in Court",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(8.dp))
      ground.requiredEvidence.forEach { doc ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = LegalGreen,
            modifier = Modifier
              .size(18.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = doc,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurface,
              lineHeight = 20.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Card(
        colors = CardDefaults.cardColors(containerColor = NeutralCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "Advocate's Practical Advice",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = NavyPrimary
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = ground.practicalTips,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 18.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = onFindLawyersClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("find_lawyers_for_ground_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
      ) {
        Icon(imageVector = Icons.Default.PersonSearch, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Find Advocates for this Matter",
          fontWeight = FontWeight.Bold,
          color = PureWhite
        )
      }
    }
  }
}
