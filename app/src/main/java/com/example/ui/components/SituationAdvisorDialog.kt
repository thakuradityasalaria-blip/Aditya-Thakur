package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class SituationOption(
  val id: String,
  val title: String,
  val description: String,
  val icon: ImageVector,
  val tag: String,
  val isWarning: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SituationAdvisorDialog(
  onDismiss: () -> Unit,
  onSelectSituation: (String) -> Unit
) {
  val situations = listOf(
    SituationOption(
      id = "mutual",
      title = "Both of us agree to separate amicably",
      description = "Fastest legal route without fault allegations. Settles alimony and custody mutually.",
      icon = Icons.Default.Handshake,
      tag = "Mutual Consent (Sec 13B)"
    ),
    SituationOption(
      id = "triple_talaq",
      title = "Husband pronounced Instant Triple Talaq",
      description = "CRITICAL: This is VOID and a punishable criminal offense under 2019 Act. Marriage is NOT dissolved.",
      icon = Icons.Default.Warning,
      tag = "Muslim Women Protection Act 2019",
      isWarning = true
    ),
    SituationOption(
      id = "muslim_wife",
      title = "Muslim wife seeking separation (Khula or Court decree)",
      description = "Dissolution initiated by wife (Khula) or 9 judicial grounds under DMMA 1939.",
      icon = Icons.Default.Balance,
      tag = "Khula & DMMA 1939"
    ),
    SituationOption(
      id = "cruelty",
      title = "Subjected to physical or mental cruelty / harassment",
      description = "Domestic violence, continuous humiliation, denial of maintenance, or dowry demands.",
      icon = Icons.Default.Shield,
      tag = "Fault Ground: Cruelty"
    ),
    SituationOption(
      id = "desertion",
      title = "Spouse left home / separated for 2+ years",
      description = "Permanent abandonment without consent and without reasonable justification.",
      icon = Icons.Default.DirectionsWalk,
      tag = "Fault Ground: Desertion"
    ),
    SituationOption(
      id = "civil_interfaith",
      title = "Civil Marriage / Interfaith Court Marriage",
      description = "Governed by Special Marriage Act 1954 for both mutual and contested divorces.",
      icon = Icons.Default.AccountBalance,
      tag = "Special Marriage Act"
    )
  )

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    modifier = Modifier.testTag("advisor_sheet")
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
            text = "Case Eligibility Advisor",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Select what best describes your marital situation",
            style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        situations.forEach { option ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onSelectSituation(option.id) }
              .testTag("advisor_option_${option.id}"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (option.isWarning) Color(0xFFFFF8E1) else NeutralCard
            )
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (option.isWarning) Color(0xFFFFE082) else NavyLight),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = option.icon,
                  contentDescription = null,
                  tint = if (option.isWarning) LegalAmber else PureWhite,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = option.title,
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                  text = option.description,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 17.sp
                  )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "→ Relevant Ground: ${option.tag}",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = if (option.isWarning) LegalRed else NavyPrimary,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
            }
          }
        }
      }
    }
  }
}
