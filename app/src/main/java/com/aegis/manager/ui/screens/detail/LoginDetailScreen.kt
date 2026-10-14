package com.aegis.manager.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisDetailRow
import com.aegis.manager.ui.components.AegisGhostButton
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisMonogram
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.AegisTopBar
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.screens.unlock.findFragmentActivity
import com.aegis.manager.ui.screens.passwords.VaultListViewModel
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisRed
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2
import android.widget.Toast

@Composable
fun LoginDetailScreen(
    itemId: Long,
    onBack: () -> Unit,
    viewModel: VaultListViewModel = hiltViewModel(),
) {
    val all by viewModel.all.collectAsStateWithLifecycle()
    val item = all.firstOrNull { it.id == itemId }
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()
        AegisTopBar(
            title = item?.title.orEmpty(),
            onNavigateBack = onBack,
            trailing = {
                AegisLinkButton(
                    text = stringResource(R.string.action_edit),
                    onClick = { },
                )
            },
        )

        if (item == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "This item is no longer in your vault.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AegisText2,
                )
            }
            return@Column
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AegisMonogram(title = item.title, size = 72)
            Box(Modifier.size(14.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = AegisText,
                textAlign = TextAlign.Center,
            )
            if (item.website.isNotBlank()) {
                Text(
                    text = item.website.removePrefix("https://"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AegisText2,
                )
            }

            Box(Modifier.size(24.dp))

            AegisDetailRow(stringResource(R.string.field_username), item.username)
            Box(Modifier.size(10.dp))
            AegisDetailRow(
                label = stringResource(R.string.field_password),
                value = MASKED,
                mono = true,
            )
            Box(Modifier.size(10.dp))
            AegisDetailRow(stringResource(R.string.field_website), item.website)
            Box(Modifier.size(10.dp))
            AegisDetailRow(
                label = stringResource(R.string.detail_last_changed),
                value = relativeTime(item.updatedAt),
            )

            if (item.notes.isNotBlank()) {
                Box(Modifier.size(10.dp))
                AegisDetailRow(stringResource(R.string.field_notes), item.notes)
            }

            Box(Modifier.size(26.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.action_copy_password),
                onClick = {
                    clipboard.setText(AnnotatedString(item.password))
                    Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show()
                },
            )

            Box(Modifier.size(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    tint = AegisRed,
                    modifier = Modifier.size(18.dp),
                )
                AegisLinkButton(
                    text = stringResource(R.string.action_delete),
                    onClick = {
                        viewModel.delete(item)
                        onBack()
                    },
                    color = AegisRed,
                )
            }

            Box(Modifier.size(32.dp))
        }
    }
}

private const val MASKED = "••••••••••••"

private fun relativeTime(timestamp: Long): String {
    if (timestamp <= 0L) return "Never"
    val days = (System.currentTimeMillis() - timestamp) / 86_400_000L
    return when {
        days <= 0L -> "Today"
        days == 1L -> "Yesterday"
        days < 7 -> "$days days ago"
        days < 14 -> "1 week ago"
        days < 60 -> "${days / 7} weeks ago"
        days < 365 -> "${days / 30} months ago"
        else -> "${days / 365} years ago"
    }
}
