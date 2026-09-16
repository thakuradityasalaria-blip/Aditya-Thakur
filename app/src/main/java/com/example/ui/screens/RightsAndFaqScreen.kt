package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LegalFaq
import com.example.data.repository.LegalRepository
import com.example.ui.theme.*

@Composable
fun RightsAndFaqScreen(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var expandedFaqId by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Top Banner
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = NavyPrimary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Fundamental Matrimonial Rights",
            color = PureWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Understand your statutory entitlements under Indian personal laws, Supreme Court precedents, and constitutional protections.",
            color = Color(0xFFD0DCE5),
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
        }
      }
    }

    // Key Rights Cards
    item {
      Text(
        text = "Key Legal Pillars",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    item {
      LegalPillarCard(
        title = "Alimony & Interim Maintenance",
        act = "Sec 125 CrPC / Sec 144 BNSS & HMA Sec 24/25",
        summary = "Every dependent spouse and child is entitled to maintenance from date of application. Supreme Court requires mandatory disclosure of all assets and ITRs (Rajnesh v. Neha).",
        bulletPoints = listOf(
          "Interim maintenance awarded within 60 days of application",
          "Permanent alimony payable either as lump sum or monthly payment",
          "Calculated on standard of living enjoyed during matrimony",
          "Non-payment can lead to attachment of salary, bank accounts, or civil imprisonment"
        )
      )
    }

    item {
      LegalPillarCard(
        title = "Child Custody & Shared Parenting",
        act = "Guardians and Wards Act, 1890",
        summary = "Under Indian jurisprudence, the 'Welfare of the Child' is paramount over parental claims.",
        bulletPoints = listOf(
          "Presumption of maternal custody for infants and toddlers below 5 years",
          "Children aged 9+ interviewed in judicial chambers for emotional preference",
          "Non-custodial parent has legal right to meaningful physical and video visitation",
          "Joint legal custody encouraged for schooling and healthcare decisions"
        )
      )
    }

    item {
      LegalPillarCard(
        title = "Protection Against Instant Triple Talaq",
        act = "Muslim Women (Protection of Rights on Marriage) Act, 2019",
        summary = "Instantaneous Triple Talaq (Talaq-e-Biddat) has zero legal effect. Aggrieved women have strong criminal and civil remedies.",
        bulletPoints = listOf(
          "Pronouncement is void; woman continues as legally wedded wife",
          "Husband faces up to 3 years imprisonment upon complaint",
          "Magistrate awards immediate subsistence allowance to wife and dependent children",
          "Wife has statutory custody right of minor children"
        ),
        isWarning = true
      )
    }

    item {
      LegalPillarCard(
        title = "Stridhan & Jewelry Absolute Ownership",
        act = "Pratibha Rani v. Suraj Kumar (Supreme Court Landmark)",
        summary = "Stridhan consists of all gifts, gold, ornaments, and articles given to the woman at or around marriage.",
        bulletPoints = listOf(
          "Absolute property of woman; husband and in-laws are only trustees",
          "Refusal to return upon separation is criminal breach of trust (Sec 406 IPC)",
          "Does not get extinguished even if divorce decree is granted"
        )
      )
    }

    // Free Legal Aid & Helpline Card
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.PhoneInTalk,
              contentDescription = null,
              tint = LegalGreen,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Official Legal Aid & Emergency Numbers",
              fontWeight = FontWeight.Bold,
              color = LegalGreen,
              fontSize = 15.sp
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "NALSA Free Legal Services: Under Section 12 of the Legal Services Authorities Act, ALL women are entitled to 100% free legal aid and government-appointed advocates irrespective of income.",
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = NavyPrimary)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedButton(
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                  data = Uri.parse("tel:15100")
                }
                context.startActivity(intent)
              },
              modifier = Modifier.weight(1f)
            ) {
              Text("NALSA 15100", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                  data = Uri.parse("tel:181")
                }
                context.startActivity(intent)
              },
              modifier = Modifier.weight(1f)
            ) {
              Text("Women 181", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // FAQ Section
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Frequently Asked Questions",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(LegalRepository.legalFaqs, key = { it.id }) { faq ->
      val isExpanded = (expandedFaqId == faq.id)
      FaqCard(
        faq = faq,
        isExpanded = isExpanded,
        onClick = {
          expandedFaqId = if (isExpanded) null else faq.id
        }
      )
    }
  }
}

@Composable
fun LegalPillarCard(
  title: String,
  act: String,
  summary: String,
  bulletPoints: List<String>,
  isWarning: Boolean = false
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isWarning) Color(0xFFFFF8E1) else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = if (isWarning) LegalAmber else NavyPrimary
            )
          )
          Text(
            text = act,
            style = MaterialTheme.typography.labelSmall.copy(
              color = SlateSecondary,
              fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = summary,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 18.sp
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        bulletPoints.forEach { point ->
          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .padding(top = 5.dp)
                .size(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(if (isWarning) LegalAmber else GoldAccent)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = point,
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun FaqCard(
  faq: LegalFaq,
  isExpanded: Boolean,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("faq_card_${faq.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(1.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = faq.question,
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.weight(1f)
        )
        Icon(
          imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = if (isExpanded) "Collapse" else "Expand",
          tint = SlateSecondary
        )
      }

      AnimatedVisibility(visible = isExpanded) {
        Column {
          Spacer(modifier = Modifier.height(8.dp))
          Divider(color = BorderSubtle, thickness = 0.6.dp)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = faq.answer,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 19.sp
            )
          )
        }
      }
    }
  }
}
