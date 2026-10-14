package com.aegis.manager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.VpnKey
import com.aegis.manager.ui.theme.AegisOnPrimary
import com.aegis.manager.ui.theme.AegisOutline
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisTeal
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

/** Primary lime CTA used across Onboarding, New Item and Unlock. */
@Composable
fun AegisPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val alpha = if (enabled) 1f else 0.4f
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(27.dp))
            .background(AegisPrimary.copy(alpha = alpha))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = AegisOnPrimary,
        )
    }
}

/** Outlined secondary CTA: "I already have an account", "Regenerate", "Copy". */
@Composable
fun AegisGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(AegisSurface2)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AegisPrimary,
                modifier = Modifier.size(18.dp),
            )
            Box(Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = AegisText,
        )
    }
}

/** Text-only link button used for "Forgot master password?" and "Continue". */
@Composable
fun AegisLinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = AegisPrimary,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = color,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}

/** The gradient key badge from the Onboarding screen, reusable as a leading mark. */
@Composable
fun AegisLogoMark(size: Int = 88, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape((size * 0.3f).dp))
            .background(
                Brush.linearGradient(listOf(AegisPrimary, AegisTeal)),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.VpnKey,
            contentDescription = null,
            tint = AegisOnPrimary,
            modifier = Modifier.size((size * 0.48f).dp),
        )
    }
}

/**
 * Monogram avatar used on every list row: a coloured tile with the first letter
 * of the site name, matching the design's list items.
 */
@Composable
fun AegisMonogram(
    title: String,
    modifier: Modifier = Modifier,
    size: Int = 44,
) {
    val palette = listOf(
        AegisPrimary,
        AegisTeal,
        AegisPrimary,
        AegisTeal,
        AegisPrimary,
        AegisTeal,
    )
    val tint = palette[(title.firstOrNull()?.code?.plus(1) ?: 0) % palette.size]
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(AegisSurface2),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title.firstOrNull()?.uppercase() ?: "?",
            style = MaterialTheme.typography.titleMedium,
            color = tint,
        )
    }
}

/** A labelled read-only row used by the detail and settings screens. */
@Composable
fun AegisDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    mono: Boolean = false,
    valueColor: Color = AegisText,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AegisSurface2, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = AegisText2,
            modifier = Modifier.width(112.dp),
        )
        Text(
            text = value,
            style = if (mono) {
                MaterialTheme.typography.bodyMedium.copy(fontFamily = com.aegis.manager.ui.theme.JetBrainsMonoFamily)
            } else {
                MaterialTheme.typography.bodyMedium
            },
            color = valueColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Small pill used for stats and filters. */
@Composable
fun AegisChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(if (selected) AegisPrimary else AegisSurface2)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) AegisOnPrimary else AegisText2,
        )
    }
}

/** Border colour shared by the dark outlined fields. */
val AegisFieldBorder = AegisOutline

internal val RefreshIcon: ImageVector get() = Icons.Filled.Refresh
internal val CopyIcon: ImageVector get() = Icons.Filled.ContentCopy