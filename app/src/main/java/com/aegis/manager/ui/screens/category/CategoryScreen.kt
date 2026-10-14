package com.aegis.manager.ui.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisListRow
import com.aegis.manager.ui.components.AegisSearchField
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.screens.passwords.VaultListViewModel
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun CategoryScreen(
    title: String,
    onOpenLogin: (Long) -> Unit,
    onAddItem: () -> Unit,
    onOpenSearch: () -> Unit,
    onSelectTab: (AegisTab) -> Unit,
    viewModel: VaultListViewModel = hiltViewModel(),
) {
    val logins by viewModel.logins.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AegisText,
                )
                Text(
                    text = stringResource(R.string.category_summary, logins.size),
                    style = MaterialTheme.typography.bodySmall,
                    color = AegisText2,
                )
            }
            IconButton(onClick = onOpenSearch) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.passwords_search_hint),
                    tint = AegisPrimary,
                )
            }
            IconButton(onClick = onAddItem) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add item",
                    tint = AegisPrimary,
                )
            }
        }

        Box(Modifier.size(6.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(logins, key = { it.id }) { item ->
                AegisListRow(
                    title = item.title,
                    subtitle = item.subtitle,
                    onClick = { onOpenLogin(item.id) },
                    trailing = if (item.favourite) "★" else null,
                )
            }
        }

        AegisBottomBar(current = AegisTab.Passwords, onSelect = onSelectTab)
    }
}