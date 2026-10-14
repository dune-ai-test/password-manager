package com.aegis.manager.ui.screens.createMaster

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aegis.manager.R
import com.aegis.manager.domain.StrengthEstimator
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.AegisStrengthRow
import com.aegis.manager.ui.components.AegisTextField
import com.aegis.manager.ui.components.AegisTopBar
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOrange
import com.aegis.manager.ui.theme.AegisOrangeSoft
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

@Composable
fun CreateMasterScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    val strength = remember(password) { StrengthEstimator.estimate(password) }
    val meetsLength = password.length >= 12
    val meetsMixed = password.any { it.isUpperCase() } && password.any { it.isLowerCase() }
    val meetsNumber = password.any { it.isDigit() } || password.any { !it.isLetterOrDigit() }
    val canContinue = meetsLength && meetsMixed && meetsNumber

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg),
    ) {
        StatusBarSpacer()
        AegisTopBar(title = "", onNavigateBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.create_master_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
            )
            Box(Modifier.size(10.dp))
            Text(
                text = stringResource(R.string.create_master_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = AegisText2,
            )

            Box(Modifier.size(26.dp))

            AegisTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(R.string.field_password),
                keyboardType = KeyboardType.Password,
                isPassword = true,
                mono = true,
            )

            Box(Modifier.size(18.dp))

            AegisStrengthRow(
                label = stringResource(R.string.create_master_strength_label),
                strength = strength,
            )

            Box(Modifier.size(22.dp))

            RuleRow(stringResource(R.string.create_master_rule_length), meetsLength)
            RuleRow(stringResource(R.string.create_master_rule_mixed), meetsMixed)
            RuleRow(stringResource(R.string.create_master_rule_number), meetsNumber)

            Box(Modifier.size(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AegisOrangeSoft)
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = AegisOrange,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = stringResource(R.string.create_master_warning),
                    style = MaterialTheme.typography.bodySmall,
                    color = AegisText,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }

            Box(Modifier.size(28.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.create_master_continue),
                onClick = onContinue,
                enabled = canContinue,
            )

            Box(Modifier.size(32.dp))
        }
    }
}

@Composable
private fun RuleRow(label: String, met: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = AegisText2,
        )
        Box(
            Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (met) AegisPrimary else AegisSurface2),
        )
    }
}