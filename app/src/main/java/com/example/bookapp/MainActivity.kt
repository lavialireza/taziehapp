package com.example.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.example.bookapp.data.Prefs
import com.example.bookapp.ui.AppNavigation
import com.example.bookapp.ui.theme.TaziehTypography

// طرح رنگی حالت شب واقعی: پس‌زمینه مشکی خالص و متن کرم‌رنگ ملایم (مناسب مطالعه طولانی)
private val TrueNightColorScheme = darkColorScheme(
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color(0xFF1A1A1A),
    onBackground = Color(0xFFEFE0C0),
    onSurface = Color(0xFFEFE0C0),
    onSurfaceVariant = Color(0xFFC9BFA6),
    primary = Color(0xFFD4A94A),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF2A230F),
    onPrimaryContainer = Color(0xFFEFE0C0)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember { mutableStateOf(Prefs.isDarkMode(this)) }
            var fontScale by remember { mutableFloatStateOf(Prefs.getFontScale(this)) }

            val colorScheme = if (darkMode) TrueNightColorScheme else lightColorScheme()
            val baseDensity = LocalDensity.current
            val scaledDensity = androidx.compose.ui.unit.Density(
                density = baseDensity.density,
                fontScale = baseDensity.fontScale * fontScale
            )

            MaterialTheme(colorScheme = colorScheme, typography = TaziehTypography) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocalDensity provides scaledDensity) {
                        AppNavigation(
                            darkMode = darkMode,
                            onDarkModeChange = {
                                darkMode = it
                                Prefs.setDarkMode(this, it)
                            },
                            fontScale = fontScale,
                            onFontScaleChange = {
                                fontScale = it
                                Prefs.setFontScale(this, it)
                            }
                        )
                    }
                }
            }
        }
    }
}
