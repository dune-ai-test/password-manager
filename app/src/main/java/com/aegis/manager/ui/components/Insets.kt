package com.aegis.manager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * The design frames draw their own status bar because they are static mockups.
 * On Android the real system bar renders there instead, so screens just reserve
 * the inset rather than painting fake time and signal icons.
 */
@Composable
fun StatusBarSpacer() {
    Box(
        Modifier
            .fillMaxWidth()
            .windowInsetsTopHeight(WindowInsets.statusBars),
    )
}

/** Reserves the navigation/gesture bar inset at the bottom of a screen. */
@Composable
fun BottomInsetSpacer() {
    Box(
        Modifier
            .fillMaxWidth()
            .windowInsetsTopHeight(WindowInsets.navigationBars),
    )
}
