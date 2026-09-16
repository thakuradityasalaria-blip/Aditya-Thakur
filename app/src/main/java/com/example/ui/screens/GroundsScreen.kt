package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.model.DivorceGround
import com.example.data.model.GroundCategory
import com.example.ui.components.GroundCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroundsScreen(
  grounds: List<DivorceGround>,
  searchQuery: String,
  selectedCategory: GroundCategory,
  bookmarkedGroundIds: Set<String>,
  onSearchQueryChange: (String) -> Unit,
  onCategorySelect: (GroundCategory) -> Unit,
  onGroundClick: (DivorceGround) -> Unit,
  onBookmarkToggle: (String) -> Unit,
  onOpenAdvisor: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Advisor Callout Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenAdvisor() }
          .testTag("open_advisor_banner"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyPrimary)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GoldAccent),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.HelpOutline,
              contentDescription = null,
              tint = NavyDark,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Case Eligibility Advisor",
              color = PureWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Unsure what ground applies? Click for guided assessment",
              color = Color(0xFFD0DCE5),
              fontSize = 12.sp
            )
          }

          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = PureWhite,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    // Search bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search by ground, act, section, or keyword...") },
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
          .testTag("grounds_search_input")
      )
    }

    // Category Filter Chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        GroundCategory.values().forEach { category ->
          val isSelected = (selectedCategory == category)
          FilterChip(
            selected = isSelected,
            onClick = { onCategorySelect(category) },
            label = {
              Text(
                text = category.displayName,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 12.sp
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = NavyPrimary,
              selectedLabelColor = PureWhite,
              containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.testTag("category_chip_${category.name}")
          )
        }
      }
    }

    // List header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Grounds for Filing Divorce (${grounds.size})",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    }

    // Empty state or Items
    if (grounds.isEmpty()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.SearchOff,
              contentDescription = null,
              tint = SlateSecondary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No grounds matching your search",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Try clearing filters or searching for terms like 'Cruelty', 'Mutual', 'Talaq', or 'Desertion'.",
              fontSize = 13.sp,
              color = SlateSecondary
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = {
                onSearchQueryChange("")
                onCategorySelect(GroundCategory.ALL)
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
              Text("Reset Filters", color = PureWhite)
            }
          }
        }
      }
    } else {
      items(grounds, key = { it.id }) { ground ->
        GroundCard(
          ground = ground,
          isBookmarked = bookmarkedGroundIds.contains(ground.id),
          onBookmarkClick = { onBookmarkToggle(ground.id) },
          onClick = { onGroundClick(ground) }
        )
      }
    }
  }
}
