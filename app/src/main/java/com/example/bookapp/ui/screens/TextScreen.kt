package com.example.bookapp.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(
    title: String,
    content: String,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                actions = {
                    IconButton(onClick = onToggleBookmark) {
                        Icon(
                            if (isBookmarked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "نشان کردن"
                        )
                    }
                    IconButton(onClick = { copyToClipboard(context, title, content) }) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = "کپی متن")
                    }
                    IconButton(onClick = { shareText(context, title, content) }) {
                        Icon(Icons.Filled.Share, contentDescription = "اشتراک‌گذاری")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(content, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

private fun copyToClipboard(context: Context, title: String, content: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(title, content)
    clipboard.setPrimaryClip(clip)
}

private fun shareText(context: Context, title: String, content: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, "$title\n\n$content")
    }
    context.startActivity(Intent.createChooser(intent, "اشتراک‌گذاری"))
}
