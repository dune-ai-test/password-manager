package com.aegis.manager.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val AegisColorScheme = darkColorScheme(
    primary = AegisPrimary,
    onPrimary = AegisOnPrimary,
    secondary = AegisTeal,
    onSecondary = AegisOnPrimary,
    background = AegisBg,
    onBackground = AegisText,
    surface = AegisSurface,
    onSurface = AegisText,
    surfaceVariant = AegisSurface2,
    onSurfaceVariant = AegisText2,
    outline = AegisOutline,
    outlineVariant = AegisOutline,
    error = AegisRed,
    onError = AegisOnPrimary,
    scrim = AegisBg,
)

private val AegisShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

/**
 * Aegis ships dark only - the design has no light variant - so
 * [isSystemInDarkTheme] is deliberately ignored.
 */
@Composable
fun AegisTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val context = LocalContext.current
        SideEffect {
            (context as? Activity)?.window?.let { window ->
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = AegisColorScheme,
        typography = AegisTypography,
        shapes = AegisShapes,
        content = content,
    )
}