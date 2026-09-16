package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChecklistItem
import com.example.data.model.ProcedureStep
import com.example.data.repository.LegalRepository
import com.example.ui.theme.*

@Composable
fun ProcedureScreen(
  completedChecklistIds: Set<String>,
  onToggleChecklistItem: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedSubTab by remember { mutableStateOf(0) } // 0: Court Stages, 1: Document Checklist

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(12.dp))

    // Segmented toggle
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(NeutralCard)
        .padding(4.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(if (selectedSubTab == 0) NavyPrimary else Color.Transparent)
          .clickable { selectedSubTab = 0 }
          .padding(vertical = 10.dp)
          .testTag("subtab_court_stages"),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AccountTree,
            contentDescription = null,
            tint = if (selectedSubTab == 0) PureWhite else NavyPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Court Procedure Stages",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = if (selectedSubTab == 0) PureWhite else NavyPrimary
          )
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(8.dp))
          .background(if (selectedSubTab == 1) NavyPrimary else Color.Transparent)
          .clickable { selectedSubTab = 1 }
          .padding(vertical = 10.dp)
          .testTag("subtab_document_checklist"),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Checklist,
            contentDescription = null,
            tint = if (selectedSubTab == 1) PureWhite else NavyPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Document Checklist",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = if (selectedSubTab == 1) PureWhite else NavyPrimary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    if (selectedSubTab == 0) {
      // Court Stages
      LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item {
          Card(
            colors = CardDefaults.cardColors(containerColor = OffWhiteSurface),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = LegalBlue,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Family Courts follow the Civil Procedure Code (CPC) with mandatory conciliation under Section 9 of Family Courts Act, 1984.",
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp)
              )
            }
          }
        }

        items(LegalRepository.procedureSteps, key = { it.stepNumber }) { step ->
          ProcedureStepCard(step = step)
        }
      }
    } else {
      // Document Checklist
      val totalItems = LegalRepository.documentChecklist.size
      val completedCount = completedChecklistIds.size
      val progress = if (totalItems > 0) completedCount.toFloat() / totalItems else 0f

      LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Progress Card
        item {
          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Documentation Readiness",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                  text = "$completedCount of $totalItems Ready",
                  fontWeight = FontWeight.Bold,
                  color = if (progress >= 1f) LegalGreen else NavyPrimary,
                  fontSize = 13.sp
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(8.dp)
                  .clip(RoundedCornerShape(4.dp)),
                color = if (progress >= 1f) LegalGreen else GoldAccent,
                trackColor = NeutralCard
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = if (progress >= 1f) "All crucial documents prepared! You are ready to consult with counsel." else "Tick each item as you collect them from records or authorities.",
                style = MaterialTheme.typography.bodySmall.copy(color = SlateSecondary)
              )
            }
          }
        }

        items(LegalRepository.documentChecklist, key = { it.id }) { item ->
          val isDone = completedChecklistIds.contains(item.id)
          DocumentChecklistRow(
            item = item,
            isCompleted = isDone,
            onToggle = { onToggleChecklistItem(item.id) }
          )
        }
      }
    }
  }
}

@Composable
fun ProcedureStepCard(step: ProcedureStep) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(1.dp),
    modifier = Modifier.fillMaxWidth().testTag("procedure_step_${step.stepNumber}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(NavyPrimary),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${step.stepNumber}",
              color = PureWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = step.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(NeutralCard)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = step.timeEstimate,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = SlateSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = step.description,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        step.keyActions.forEach { action ->
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier
                .size(15.dp)
                .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = action,
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(OffWhiteSurface)
          .padding(8.dp)
      ) {
        Text(
          text = "Legal note: ${step.statutoryNote}",
          style = MaterialTheme.typography.labelSmall.copy(
            color = SlateSecondary,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
          )
        )
      }
    }
  }
}

@Composable
fun DocumentChecklistRow(
  item: ChecklistItem,
  isCompleted: Boolean,
  onToggle: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onToggle() }
      .testTag("checklist_item_${item.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isCompleted) Color(0xFFF1F8E9) else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(1.dp)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.Top
    ) {
      Checkbox(
        checked = isCompleted,
        onCheckedChange = { onToggle() },
        colors = CheckboxDefaults.colors(
          checkedColor = LegalGreen,
          checkmarkColor = PureWhite
        ),
        modifier = Modifier.padding(top = 0.dp)
      )

      Spacer(modifier = Modifier.width(6.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = item.title,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = if (isCompleted) LegalGreen else MaterialTheme.colorScheme.onSurface
            )
          )
          if (item.isMandatory) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFFFEBEE))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "Required",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = LegalRed
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = item.description,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 17.sp
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Registry Tip: ${item.tips}",
          style = MaterialTheme.typography.labelSmall.copy(
            color = SlateSecondary,
            lineHeight = 15.sp
          )
        )
      }
    }
  }
}
