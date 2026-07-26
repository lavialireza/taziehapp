package com.example.bookapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bookapp.R

val TaziehFontFamily = FontFamily(
    Font(R.font.b_titr_bold, FontWeight.Normal),
    Font(R.font.b_titr_bold, FontWeight.Bold)
)

val TaziehTypography = Typography(
    displayLarge = TextStyle(fontFamily = TaziehFontFamily),
    displayMedium = TextStyle(fontFamily = TaziehFontFamily),
    displaySmall = TextStyle(fontFamily = TaziehFontFamily),
    headlineLarge = TextStyle(fontFamily = TaziehFontFamily),
    headlineMedium = TextStyle(fontFamily = TaziehFontFamily),
    headlineSmall = TextStyle(fontFamily = TaziehFontFamily),
    titleLarge = TextStyle(fontFamily = TaziehFontFamily),
    titleMedium = TextStyle(fontFamily = TaziehFontFamily),
    titleSmall = TextStyle(fontFamily = TaziehFontFamily),
    bodyLarge = TextStyle(fontFamily = TaziehFontFamily, fontSize = 18.sp, lineHeight = 32.sp),
    bodyMedium = TextStyle(fontFamily = TaziehFontFamily),
    bodySmall = TextStyle(fontFamily = TaziehFontFamily),
    labelLarge = TextStyle(fontFamily = TaziehFontFamily),
    labelMedium = TextStyle(fontFamily = TaziehFontFamily),
    labelSmall = TextStyle(fontFamily = TaziehFontFamily)
)
