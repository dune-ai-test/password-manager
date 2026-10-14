package com.aegis.manager.ui.screens.settings

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aegis.manager.BuildConfig
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisMonogram
import com.aegis.manager.ui.components.AegisSettingRow
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.AegisToggle
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisTeal
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun SettingsScreen(onSelectTab: (AegisTab) -> Unit) {
    var autoLockOn by remember { mutableStateOf(true) }
    var biometricOn by remember { mutableStateOf(false) }
    var twoFactorOn by remember { mutableStateOf(true) }
    var autofillOn by remember { mutableStateOf(true) }

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
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
            )

            Box(Modifier.size(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AegisMonogram(title = stringResource(R.string.settings_profile_name), size = 52)
                Text(
                    text = stringResource(R.string.settings_profile_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = AegisText,
                    modifier = Modifier.padding(start = 14.dp),
                )
            }

            Box(Modifier.size(18.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_auto_lock),
                icon = Icons.Outlined.Lock,
                value = stringResource(R.string.settings_one_minute),
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_biometric),
                icon = Icons.Outlined.Fingerprint,
                value = if (biometricOn) stringResource(R.string.settings_on) else {
                    stringResource(R.string.settings_biometric_value)
                },
                onClick = { biometricOn = !biometricOn },
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_two_factor),
                icon = Icons.Outlined.VerifiedUser,
                iconTint = AegisTeal,
                trailing = {
                    AegisToggle(twoFactorOn) { twoFactorOn = it }
                },
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_autofill),
                icon = Icons.Outlined.Autorenew,
                value = if (autofillOn) stringResource(R.string.settings_on) else "Off",
                onClick = { autofillOn = !autofillOn },
            )

            Box(Modifier.size(18.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_import_export),
                icon = Icons.Outlined.CloudUpload,
                iconTint = AegisTeal,
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_backup),
                value = stringResource(R.string.settings_backup_today),
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_shared),
                icon = Icons.Outlined.Groups,
                iconTint = AegisTeal,
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_help),
                icon = Icons.Outlined.HelpOutline,
                iconTint = AegisTeal,
            )
            Box(Modifier.size(8.dp))

            AegisSettingRow(
                label = stringResource(R.string.settings_about),
                icon = Icons.Outlined.Info,
                iconTint = AegisTeal,
            )

            Box(Modifier.size(14.dp))

            Text(
                text = stringResource(
                    R.string.settings_version,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE,
                ),
                style = MaterialTheme.typography.labelSmall,
                color = AegisText2,
                modifier = Modifier.padding(start = 4.dp),
            )

            Box(Modifier.size(28.dp))
        }

        AegisBottomBar(current = AegisTab.Settings, onSelect = onSelectTab)
    }
}