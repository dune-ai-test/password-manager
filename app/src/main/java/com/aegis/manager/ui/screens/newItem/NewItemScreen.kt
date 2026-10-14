package com.aegis.manager.ui.screens.newItem

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisChip
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisNotesField
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.AegisSectionLabel
import com.aegis.manager.ui.components.AegisTextField
import com.aegis.manager.ui.components.AegisTopBar
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun NewItemScreen(
    onDone: () -> Unit,
    viewModel: NewItemViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()
        AegisTopBar(
            title = stringResource(R.string.new_item_title),
            onNavigateBack = onDone,
            trailing = {
                AegisLinkButton(
                    text = stringResource(R.string.action_save),
                    onClick = {
                        viewModel.save()
                        onDone()
                    },
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            AegisSectionLabel(stringResource(R.string.new_item_type_login))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AegisChip(
                    text = stringResource(R.string.new_item_type_login),
                    selected = true,
                    onClick = { },
                )
            }

            Box(Modifier.size(18.dp))

            AegisTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                placeholder = stringResource(R.string.field_title),
            )
            Box(Modifier.size(10.dp))
            AegisTextField(
                value = state.username,
                onValueChange = viewModel::onUsernameChange,
                placeholder = stringResource(R.string.field_username),
                keyboardType = KeyboardType.Email,
            )
            Box(Modifier.size(10.dp))
            AegisTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = stringResource(R.string.field_password),
                keyboardType = KeyboardType.Password,
                isPassword = true,
                mono = true,
            )

            Box(Modifier.size(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AegisPrimary,
                    modifier = Modifier.size(15.dp),
                )
                AegisLinkButton(
                    text = "${stringResource(R.string.new_item_suggested)}: ${state.suggestion}",
                    onClick = {
                        viewModel.useSuggestion()
                        clipboard.setText(AnnotatedString(state.suggestion))
                    },
                )
            }

            Box(Modifier.size(10.dp))
            AegisTextField(
                value = state.website,
                onValueChange = viewModel::onWebsiteChange,
                placeholder = stringResource(R.string.field_website),
                keyboardType = KeyboardType.Uri,
            )

            Box(Modifier.size(14.dp))
            AegisSectionLabel(stringResource(R.string.field_notes))
            AegisNotesField(
                value = state.notes,
                onValueChange = viewModel::onNotesChange,
                placeholder = "Anything worth remembering about this login",
            )

            Box(Modifier.size(24.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.new_item_save),
                onClick = {
                    viewModel.save()
                    onDone()
                },
                enabled = state.title.isNotBlank(),
            )

            Box(Modifier.size(32.dp))
        }
    }
}
