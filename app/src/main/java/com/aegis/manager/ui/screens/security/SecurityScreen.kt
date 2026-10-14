package com.aegis.manager.ui.screens.security

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
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisGhostButton
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisMonogram
import com.aegis.manager.ui.components.AegisScoreRing
import com.aegis.manager.ui.components.AegisSectionLabel
import com.aegis.manager.ui.components.AegisStatTile
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOrange
import com.aegis.manager.ui.theme.AegisOrangeSoft
import com.aegis.manager.ui.theme.AegisPurple
import com.aegis.manager.ui.theme.AegisRed
import com.aegis.manager.ui.theme.AegisRedSoft
import com.aegis.manager.ui.theme.AegisSurface
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun SecurityScreen(
    onSelectTab: (AegisTab) -> Unit,
    viewModel: SecurityViewModel = hiltViewModel(),
) {
    val report by viewModel.report.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(R.string.security_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
            )

            Box(Modifier.size(20.dp))

            AegisScoreRing(score = report.score, label = report.grade)
            Box(Modifier.size(14.dp))

            Text(
                text = stringResource(R.string.security_score),
                style = MaterialTheme.typography.titleMedium,
                color = AegisText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(Modifier.size(6.dp))
            Text(
                text = report.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = AegisText2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(Modifier.size(6.dp))
            Text(
                text = stringResource(R.string.security_last_checked),
                style = MaterialTheme.typography.labelSmall,
                color = AegisText2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Box(Modifier.size(22.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AegisStatTile(
                    count = report.weakCount,
                    label = stringResource(R.string.security_weak),
                    color = AegisRed,
                    modifier = Modifier.weight(1f),
                )
                AegisStatTile(
                    count = report.reusedCount,
                    label = stringResource(R.string.security_reused),
                    color = AegisOrange,
                    modifier = Modifier.weight(1f),
                )
                AegisStatTile(
                    count = report.missingTwoFactorCount,
                    label = stringResource(R.string.security_missing_2fa),
                    color = AegisPurple,
                    modifier = Modifier.weight(1f),
                )
                AegisStatTile(
                    count = report.breachedCount,
                    label = stringResource(R.string.security_breached),
                    color = AegisRed,
                    modifier = Modifier.weight(1f),
                )
            }

            Box(Modifier.size(24.dp))

            report.breachEntries.firstOrNull()?.let { breach ->
                AlertCard(
                    title = stringResource(R.string.security_breach_title),
                    body = stringResource(R.string.security_breach_body),
                    tint = AegisRed,
                    surface = AegisRedSoft,
                    action = stringResource(R.string.security_breach_review),
                )
                Box(Modifier.size(12.dp))
            }

            report.reuseGroups.firstOrNull()?.let { group ->
                AlertCard(
                    title = stringResource(R.string.security_reuse_title),
                    body = reuseBody(group.items.size),
                    tint = AegisOrange,
                    surface = AegisOrangeSoft,
                    action = stringResource(R.string.security_reuse_fix),
                )
                Box(Modifier.size(12.dp))
            }

            if (report.weakEntries.isNotEmpty()) {
                AegisSectionLabel(stringResource(R.string.security_weak_title))
                report.weakEntries.forEach { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(AegisSurface)
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AegisMonogram(title = entry.item.title, size = 38)
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(start = 12.dp),
                        ) {
                            Text(
                                text = entry.item.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = AegisText,
                            )
                            Text(
                                text = entry.note,
                                style = MaterialTheme.typography.bodySmall,
                                color = AegisText2,
                            )
                        }
                        AegisLinkButton(
                            text = stringResource(R.string.security_reuse_fix),
                            onClick = { },
                        )
                    }
                    Box(Modifier.size(8.dp))
                }
            }

            Box(Modifier.size(28.dp))
        }

        AegisBottomBar(current = AegisTab.Security, onSelect = onSelectTab)
    }
}

@Composable
private fun AlertCard(
    title: String,
    body: String,
    tint: androidx.compose.ui.graphics.Color,
    surface: androidx.compose.ui.graphics.Color,
    action: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(surface)
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Report,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = AegisText,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        Box(Modifier.size(8.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodySmall,
            color = AegisText2,
        )
        Box(Modifier.size(14.dp))
        AegisGhostButton(
            text = action,
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun reuseBody(count: Int) =
    "$count accounts share one password. Update takes ~2 min."
