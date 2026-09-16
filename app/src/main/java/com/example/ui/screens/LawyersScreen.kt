package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.Lawyer
import com.example.ui.components.LawyerCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawyersScreen(
  lawyers: List<Lawyer>,
  searchQuery: String,
  selectedCity: String,
  selectedSpecialization: String,
  bookmarkedLawyerIds: Set<String>,
  lawyerAddedMessage: String? = null,
  onDismissLawyerAddedMessage: () -> Unit = {},
  onAddLawyerClick: () -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onCitySelect: (String) -> Unit,
  onSpecializationSelect: (String) -> Unit,
  onLawyerClick: (Lawyer) -> Unit,
  onBookClick: (Lawyer) -> Unit,
  onBookmarkToggle: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val cities = listOf(
    "All Cities",
    "New Delhi",
    "Mumbai",
    "Bengaluru",
    "Lucknow",
    "Kolkata",
    "Chandigarh",
    "Hyderabad",
    "Chennai"
  )

  val specializations = listOf(
    "All Specializations",
    "Mutual Consent",
    "Muslim Law",
    "Child Custody",
    "Alimony",
    "Mediation",
    "Contested Divorce"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Success confirmation banner if lawyer was just added
    lawyerAddedMessage?.let { msg ->
      item {
        Card(
          colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("lawyer_added_success_banner")
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = LegalGreen,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = msg,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1B5E20)
              ),
              modifier = Modifier.weight(1f)
            )
            IconButton(
              onClick = onDismissLawyerAddedMessage,
              modifier = Modifier.size(24.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Dismiss",
                tint = Color(0xFF1B5E20),
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // Advocate Onboarding Callout Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyer_join_directory_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyPrimary)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(GoldAccent),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.PersonAdd,
              contentDescription = null,
              tint = NavyDark,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Are you a Matrimonial Advocate?",
              color = PureWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Register with name, phone, photo & email to receive client inquiries",
              color = Color(0xFFD0DCE5),
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            onClick = onAddLawyerClick,
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            modifier = Modifier.testTag("btn_open_add_lawyer")
          ) {
            Text(
              text = "+ Add Profile",
              color = NavyDark,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }
        }
      }
    }

    // Search input
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search advocate, court, city, or specialization...") },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = SlateSecondary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchQueryChange("") }) {
              Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedBorderColor = BorderSubtle
        ),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("lawyers_search_input")
      )
    }

    // City Filter Chips
    item {
      Column {
        Text(
          text = "Select City / Jurisdiction",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = SlateSecondary
          ),
          modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          cities.forEach { city ->
            val isSelected = (selectedCity == city)
            FilterChip(
              selected = isSelected,
              onClick = { onCitySelect(city) },
              label = {
                Text(
                  text = city,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 12.sp
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = NavyPrimary,
                selectedLabelColor = PureWhite,
                containerColor = MaterialTheme.colorScheme.surface
              ),
              modifier = Modifier.testTag("city_chip_$city")
            )
          }
        }
      }
    }

    // Specialization Filter Chips
    item {
      Column {
        Text(
          text = "Legal Practice Area",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = SlateSecondary
          ),
          modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          specializations.forEach { spec ->
            val isSelected = (selectedSpecialization == spec)
            FilterChip(
              selected = isSelected,
              onClick = { onSpecializationSelect(spec) },
              label = {
                Text(
                  text = spec,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 12.sp
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = GoldDark,
                selectedLabelColor = PureWhite,
                containerColor = MaterialTheme.colorScheme.surface
              ),
              modifier = Modifier.testTag("spec_chip_$spec")
            )
          }
        }
      }
    }

    // Results Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Verified Advocates (${lawyers.size})",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
          TextButton(
            onClick = onAddLawyerClick,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
            modifier = Modifier.testTag("btn_header_add_lawyer")
          ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = NavyPrimary)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add Myself", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
          }
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Verified",
            style = MaterialTheme.typography.labelSmall.copy(color = LegalGreen, fontWeight = FontWeight.Bold)
          )
        }
      }
    }

    // Lawyers list
    if (lawyers.isEmpty()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.PersonOff,
              contentDescription = null,
              tint = SlateSecondary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No advocates found matching criteria",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Try clearing the city or specialization filters.",
              fontSize = 13.sp,
              color = SlateSecondary
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = {
                onSearchQueryChange("")
                onCitySelect("All Cities")
                onSpecializationSelect("All Specializations")
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
              Text("Reset Lawyer Filters", color = PureWhite)
            }
          }
        }
      }
    } else {
      items(lawyers, key = { it.id }) { lawyer ->
        LawyerCard(
          lawyer = lawyer,
          isBookmarked = bookmarkedLawyerIds.contains(lawyer.id),
          onBookmarkClick = { onBookmarkToggle(lawyer.id) },
          onCardClick = { onLawyerClick(lawyer) },
          onBookClick = { onBookClick(lawyer) }
        )
      }
    }
  }
}
