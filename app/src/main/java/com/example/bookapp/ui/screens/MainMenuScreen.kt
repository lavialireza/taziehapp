package com.example.bookapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onOpenTaziehList: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenVersion: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("تعزیه") }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
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
        }
    }
}
