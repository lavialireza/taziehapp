package com.example.bookapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookapp.data.FieldEntity
import com.example.bookapp.data.SearchResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    fields: List<FieldEntity>,
    onSearch: suspend (String, Long?) -> List<SearchResult>,
    onResultClick: (SearchResult) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<SearchResult>()) }
    var searched by remember { mutableStateOf(false) }
    var selectedFieldId by remember { mutableStateOf<Long?>(null) }

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

            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFieldId == null,
                        onClick = { selectedFieldId = null },
                        label = { Text("همه زمینه‌ها") }
                    )
                }
                items(fields) { field ->
                    FilterChip(
                        selected = selectedFieldId == field.id,
                        onClick = { selectedFieldId = field.id },
                        label = { Text(field.title) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            LaunchedEffect(query, selectedFieldId) {
                if (query.trim().length >= 2) {
                    results = onSearch(query.trim(), selectedFieldId)
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
