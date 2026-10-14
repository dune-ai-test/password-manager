package com.aegis.manager.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisChip
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisListRow
import com.aegis.manager.ui.components.AegisSearchField
import com.aegis.manager.ui.components.AegisSectionLabel
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onOpenLogin: (Long) -> Unit,
    onSelectTab: (AegisTab) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 20.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AegisSearchField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                placeholder = stringResource(R.string.passwords_search_hint),
                modifier = Modifier.weight(1f),
                trailing = {
                    if (state.query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Clear",
                                tint = AegisText2,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                },
            )
            AegisLinkButton(
                text = stringResource(R.string.action_cancel),
                onClick = onBack,
                color = AegisText,
            )
        }

        Box(Modifier.size(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AegisChip(
                text = stringResource(R.string.search_filter_all),
                selected = state.filterAll,
                onClick = viewModel::showAll,
            )
            AegisChip(
                text = stringResource(R.string.search_filter_logins),
                selected = !state.filterAll,
                onClick = viewModel::showLoginsOnly,
            )
        }

        Box(Modifier.size(14.dp))

        if (state.results.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    text = stringResource(R.string.search_try_heading),
                    style = MaterialTheme.typography.titleSmall,
                    color = AegisText,
                )
                Box(Modifier.size(12.dp))
                SUGGESTIONS.forEach { suggestion ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(AegisSurface2)
                            .padding(horizontal = 18.dp, vertical = 11.dp),
                    ) {
                        Text(
                            text = suggestion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AegisText2,
                        )
                    }
                }
            }
        } else {
            Text(
                text = stringResource(
                    R.string.search_results_for,
                    state.results.size,
                    state.query,
                ),
                style = MaterialTheme.typography.titleSmall,
                color = AegisText,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Box(Modifier.size(12.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.results, key = { it.id }) { item ->
                    AegisListRow(
                        title = item.title,
                        subtitle = item.subtitle,
                        onClick = { onOpenLogin(item.id) },
                    )
                }
            }
        }

        AegisBottomBar(current = AegisTab.Passwords, onSelect = onSelectTab)
    }
}

private val SUGGESTIONS = listOf("work email", "passport", "wifi")
