package com.aegis.manager.ui.screens.generator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisBottomBar
import com.aegis.manager.ui.components.AegisGhostButton
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.AegisSectionLabel
import com.aegis.manager.ui.components.AegisStrengthRow
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.components.AegisToggle
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOutline
import com.aegis.manager.ui.theme.AegisOnPrimary
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2
import com.aegis.manager.ui.theme.JetBrainsMonoFamily

@Composable
fun GeneratorScreen(
    onSelectTab: (AegisTab) -> Unit,
    viewModel: GeneratorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

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
                text = stringResource(R.string.generator_title),
                style = MaterialTheme.typography.headlineSmall,
                color = AegisText,
            )

            Box(Modifier.size(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(AegisSurface2)
                    .padding(20.dp),
            ) {
                Text(
                    text = state.password,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = JetBrainsMonoFamily,
                    ),
                    color = AegisText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Box(Modifier.size(16.dp))

            AegisStrengthRow(
                label = stringResource(R.string.generator_strength),
                strength = state.strength,
            )

            Box(Modifier.size(18.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AegisGhostButton(
                    text = stringResource(R.string.generator_regenerate),
                    onClick = viewModel::regenerate,
                    icon = Icons.Filled.Refresh,
                    modifier = Modifier.weight(1f),
                )
                AegisGhostButton(
                    text = stringResource(R.string.action_copy),
                    onClick = {
                        clipboard.setText(AnnotatedString(state.password))
                        Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show()
                    },
                    icon = Icons.Filled.ContentCopy,
                    modifier = Modifier.weight(1f),
                )
            }

            Box(Modifier.size(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AegisSectionLabel(stringResource(R.string.generator_length))
                Text(
                    text = state.length.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = AegisPrimary,
                )
            }

            Slider(
                value = state.length.toFloat(),
                onValueChange = viewModel::onLengthChange,
                valueRange = 8f..64f,
                steps = 55,
                colors = SliderDefaults.colors(
                    thumbColor = AegisPrimary,
                    activeTrackColor = AegisPrimary,
                    inactiveTrackColor = AegisOutline,
                ),
            )

            Box(Modifier.size(10.dp))

            ToggleRow(stringResource(R.string.generator_uppercase), state.uppercase, viewModel::onUppercaseChange)
            ToggleRow(stringResource(R.string.generator_lowercase), state.lowercase, viewModel::onLowercaseChange)
            ToggleRow(stringResource(R.string.generator_numbers), state.numbers, viewModel::onNumbersChange)
            ToggleRow(stringResource(R.string.generator_symbols), state.symbols, viewModel::onSymbolsChange)
            ToggleRow(
                stringResource(R.string.generator_avoid_lookalikes),
                state.avoidLookAlikes,
                viewModel::onAvoidLookAlikesChange,
            )

            Box(Modifier.size(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AegisSurface2)
                    .padding(4.dp),
            ) {
                ModeTab(
                    label = stringResource(R.string.generator_random),
                    selected = state.memorable.not(),
                    onClick = { viewModel.onMemorableChange(false) },
                    modifier = Modifier.weight(1f),
                )
                ModeTab(
                    label = stringResource(R.string.generator_memorable),
                    selected = state.memorable,
                    onClick = { viewModel.onMemorableChange(true) },
                    modifier = Modifier.weight(1f),
                )
            }

            Box(Modifier.size(20.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.generator_generate),
                onClick = viewModel::regenerate,
            )

            Box(Modifier.size(28.dp))
        }

        AegisBottomBar(current = AegisTab.Generator, onSelect = onSelectTab)
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
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
        AegisToggle(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun ModeTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(13.dp))
            .background(if (selected) AegisPrimary else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 11.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = if (selected) AegisOnPrimary else AegisText2,
        )
    }
}
