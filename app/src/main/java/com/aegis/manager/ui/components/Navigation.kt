package com.aegis.manager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOutline
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface
import com.aegis.manager.ui.theme.AegisText2

enum class AegisTab(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Passwords("passwords", "Passwords", Icons.Outlined.Folder),
    Generator("generator", "Generator", Icons.Outlined.AutoAwesome),
    Security("security", "Security", Icons.Outlined.Security),
    Settings("settings", "Settings", Icons.Outlined.Settings),
}

/** The four-tab bottom bar shown on every primary tab screen. */
@Composable
fun AegisBottomBar(
    current: AegisTab,
    onSelect: (AegisTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(AegisBg),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AegisOutline),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AegisTab.entries.forEach { tab ->
                val selected = tab == current
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelect(tab) }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (selected) AegisPrimary else AegisText2,
                        modifier = Modifier.size(22.dp),
                    )
                    Box(Modifier.height(4.dp))
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) AegisPrimary else AegisText2,
                    )
                }
            }
        }
    }
}

/** List row with monogram, title, subtitle and optional trailing text. */
@Composable
fun AegisListRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailing: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AegisSurface)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AegisMonogram(title = title)
        Box(Modifier.size(0.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = AegisText2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = AegisText2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (trailing != null) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(AegisSurface)
                    .padding(horizontal = 8.dp, vertical = 3.dp),
            ) {
                Text(
                    text = trailing,
                    style = MaterialTheme.typography.labelSmall,
                    color = AegisText2,
                )
            }
        }
    }
}

/** Settings-style row: leading icon tile, label, optional value or switch. */
@Composable
fun AegisSettingRow(
    label: String,
    modifier: Modifier = Modifier,
    value: String? = null,
    icon: ImageVector? = null,
    iconTint: androidx.compose.ui.graphics.Color = AegisPrimary,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AegisSurface)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Box(
                Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(AegisSurface2),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(19.dp),
                )
            }
            Box(Modifier.size(0.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = com.aegis.manager.ui.theme.AegisText,
            modifier = Modifier
                .weight(1f)
                .padding(start = if (icon != null) 14.dp else 0.dp),
        )
        if (trailing != null) {
            trailing()
        } else if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = AegisText2,
            )
        }
    }
}