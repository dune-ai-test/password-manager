package com.aegis.manager.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aegis.manager.R

val InterFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

val JetBrainsMonoFamily = FontFamily(
    Font(R.font.jetbrains_mono_regular, FontWeight.Normal),
    Font(R.font.jetbrains_mono_medium, FontWeight.Medium),
    Font(R.font.jetbrains_mono_bold, FontWeight.Bold),
)

private fun inter(size: Int, weight: FontWeight, lineHeight: Int, letterSpacing: Double = 0.0) =
    TextStyle(
        fontFamily = InterFamily,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = letterSpacing.sp,
    )

val AegisTypography = Typography(
    displaySmall = inter(34, FontWeight.Bold, 40),
    headlineMedium = inter(28, FontWeight.Bold, 34),
    headlineSmall = inter(24, FontWeight.Bold, 30),
    titleLarge = inter(20, FontWeight.Bold, 26),
    titleMedium = inter(17, FontWeight.SemiBold, 22),
    titleSmall = inter(15, FontWeight.SemiBold, 20),
    bodyLarge = inter(15, FontWeight.Normal, 22),
    bodyMedium = inter(14, FontWeight.Normal, 20),
    bodySmall = inter(13, FontWeight.Normal, 18),
    labelLarge = inter(15, FontWeight.SemiBold, 20),
    labelMedium = inter(13, FontWeight.Medium, 18),
    labelSmall = inter(11, FontWeight.Medium, 16, letterSpacing = 0.4),
)