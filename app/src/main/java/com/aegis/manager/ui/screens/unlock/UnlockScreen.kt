package com.aegis.manager.ui.screens.unlock

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
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisGhostButton
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisLogoMark
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.AegisTextField
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOutline
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun UnlockScreen(
    onUnlock: () -> Unit,
    viewModel: UnlockViewModel = hiltViewModel(),
) {
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(Modifier.size(40.dp))
            AegisLogoMark(size = 88)
            Box(Modifier.size(28.dp))

            Text(
                text = stringResource(R.string.unlock_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
                textAlign = TextAlign.Center,
            )
            Box(Modifier.size(10.dp))
            Text(
                text = stringResource(R.string.unlock_subtitle, viewModel.itemCount),
                style = MaterialTheme.typography.bodyMedium,
                color = AegisText2,
                textAlign = TextAlign.Center,
            )

            Box(Modifier.size(30.dp))

            AegisTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(R.string.field_password),
                keyboardType = KeyboardType.Password,
                isPassword = true,
                mono = true,
            )

            Box(Modifier.size(16.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.unlock_action),
                onClick = onUnlock,
                enabled = password.isNotEmpty(),
            )

            Box(Modifier.size(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .size(1.dp)
                        .background(AegisOutline),
                )
                Text(
                    text = stringResource(R.string.unlock_or),
                    style = MaterialTheme.typography.bodySmall,
                    color = AegisText2,
                    modifier = Modifier.padding(horizontal = 14.dp),
                )
                Box(
                    Modifier
                        .weight(1f)
                        .size(1.dp)
                        .background(AegisOutline),
                )
            }

            Box(Modifier.size(20.dp))

            AegisGhostButton(
                text = stringResource(R.string.unlock_biometric),
                onClick = { promptBiometric(context, onUnlock) },
                icon = Icons.Outlined.Fingerprint,
                modifier = Modifier.fillMaxWidth(),
            )

            Box(Modifier.size(10.dp))

            AegisLinkButton(
                text = stringResource(R.string.unlock_forgot),
                onClick = { },
            )

            Box(Modifier.size(32.dp))
        }
    }
}