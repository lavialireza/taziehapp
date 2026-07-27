package com.example.bookapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.bookapp.data.SearchResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    randomVerse: SearchResult?,
    recentItems: List<SearchResult>,
    onOpenTaziehList: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenBookmarks: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenVersion: () -> Unit,
    onItemClick: (SearchResult) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("تعزیه") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            if (randomVerse != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onItemClick(randomVerse) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "بیت امروز",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            randomVerse.sectionTitle,
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "${randomVerse.taziehTitle} ← ${randomVerse.roleTitle}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            ListItem(
                headlineContent = { Text("لیست تعزیه‌ها") },
                leadingContent = { Icon(Icons.Filled.List, contentDescription = null) },
                modifier = Modifier.clickable { onOpenTaziehList() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("جستجو") },
                leadingContent = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier.clickable { onOpenSearch() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("علاقه‌مندی‌ها") },
                leadingContent = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                modifier = Modifier.clickable { onOpenBookmarks() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("درباره برنامه") },
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = null) },
                modifier = Modifier.clickable { onOpenAbout() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("تنظیمات") },
                leadingContent = { Icon(Icons.Filled.Settings, contentDescription = null) },
                modifier = Modifier.clickable { onOpenSettings() }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("ورژن برنامه") },
                modifier = Modifier.clickable { onOpenVersion() }
            )
            HorizontalDivider()

            if (recentItems.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "اخیراً مشاهده‌شده",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
                recentItems.forEach { item ->
                    ListItem(
                        headlineContent = { Text(item.sectionTitle) },
                        supportingContent = { Text("${item.taziehTitle} ← ${item.roleTitle}") },
                        leadingContent = { Icon(Icons.Filled.History, contentDescription = null) },
                        modifier = Modifier.clickable { onItemClick(item) }
                    )
                    HorizontalDivider()
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
