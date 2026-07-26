package com.example.bookapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookapp.data.SearchResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: suspend (String) -> List<SearchResult>,
    onResultClick: (SearchResult) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<SearchResult>()) }
    var searched by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جستجو") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("جستجو در عناوین و متن اشعار...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            LaunchedEffect(query) {
                if (query.trim().length >= 2) {
                    results = onSearch(query.trim())
                    searched = true
                } else {
                    results = emptyList()
                    searched = false
                }
            }

            if (searched && results.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("نتیجه‌ای یافت نشد")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(results) { r ->
                        ListItem(
                            headlineContent = { Text(r.sectionTitle) },
                            supportingContent = { Text("${r.fieldTitle} ← ${r.taziehTitle} ← ${r.roleTitle}") },
                            modifier = Modifier.clickable { onResultClick(r) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
