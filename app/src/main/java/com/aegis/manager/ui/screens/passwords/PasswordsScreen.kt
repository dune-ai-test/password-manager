package com.aegis.manager.ui.screens.passwords

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.data.model.VaultItem
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisChip
import com.aegis.manager.ui.components.AegisListRow
import com.aegis.manager.ui.components.AegisSearchField
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisText

@Composable
fun PasswordsScreen(
    onOpenCategory: () -> Unit,
    onOpenLogin: (Long) -> Unit,
    onAddItem: () -> Unit,
    onOpenSearch: () -> Unit,
    onSelectTab: (AegisTab) -> Unit,
    viewModel: VaultListViewModel = hiltViewModel(),
) {
    val items by viewModel.logins.collectAsStateWithLifecycle()
    var filter by remember { mutableStateOf(FILTER_ALL) }

    val visible = when (filter) {
        FILTER_ALL -> items
        else -> items.filter { it.title.contains(filter, ignoreCase = true) }
    }

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
            Text(
                text = stringResource(R.string.passwords_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
                modifier = Modifier.weight(1f),
            )
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

        AegisSearchField(
            value = "",
            onValueChange = { onOpenSearch() },
            placeholder = stringResource(R.string.passwords_search_hint),
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        Box(Modifier.size(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AegisChip(FILTER_ALL, filter == FILTER_ALL, { filter = FILTER_ALL })
            AegisChip(FILTER_FAVOURITES, filter == FILTER_FAVOURITES, { filter = FILTER_FAVOURITES })
            AegisChip(FILTER_SECURE, filter == FILTER_SECURE, { filter = FILTER_SECURE })
        }

        Box(Modifier.size(14.dp))

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
            items(visible, key = { it.id }) { item ->
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

private const val FILTER_ALL = "All"
private const val FILTER_FAVOURITES = "Favourites"
private const val FILTER_SECURE = "Secure notes"
