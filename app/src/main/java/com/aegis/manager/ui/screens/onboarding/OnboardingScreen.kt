package com.aegis.manager.ui.screens.onboarding

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
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aegis.manager.R
import com.aegis.manager.ui.components.AegisLogoMark
import com.aegis.manager.ui.components.AegisLinkButton
import com.aegis.manager.ui.components.AegisPrimaryButton
import com.aegis.manager.ui.components.StatusBarSpacer
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2
import com.aegis.manager.ui.theme.AegisTeal

@Composable
fun OnboardingScreen(
    onCreateMaster: () -> Unit,
    onSignIn: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AegisBg)
            .verticalScroll(rememberScrollState()),
    ) {
        StatusBarSpacer()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AegisLogoMark(size = 88)

            Box(Modifier.size(28.dp))

            Text(
                text = stringResource(R.string.onboarding_title),
                style = MaterialTheme.typography.headlineMedium,
                color = AegisText,
                textAlign = TextAlign.Center,
            )

            Box(Modifier.size(12.dp))

            Text(
                text = stringResource(R.string.onboarding_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = AegisText2,
                textAlign = TextAlign.Center,
            )

            Box(Modifier.size(26.dp))

            FeatureCard(
                icon = Icons.Outlined.Fingerprint,
                tint = AegisTeal,
                title = stringResource(R.string.onboarding_feature_biometric_title),
                body = stringResource(R.string.onboarding_feature_biometric_body),
            )
            Box(Modifier.size(10.dp))
            FeatureCard(
                icon = Icons.Outlined.Shield,
                tint = com.aegis.manager.ui.theme.AegisPrimary,
                title = stringResource(R.string.onboarding_feature_zero_title),
                body = stringResource(R.string.onboarding_feature_zero_body),
            )

            Box(Modifier.size(28.dp))

            AegisPrimaryButton(
                text = stringResource(R.string.onboarding_cta_create),
                onClick = onCreateMaster,
            )

            Box(Modifier.size(10.dp))

            AegisLinkButton(
                text = stringResource(R.string.onboarding_cta_existing),
                onClick = onSignIn,
            )

            Box(Modifier.size(24.dp))

            Text(
                text = stringResource(R.string.onboarding_legal),
                style = MaterialTheme.typography.labelSmall,
                color = AegisText2,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun FeatureCard(
    icon: ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    title: String,
    body: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AegisSurface2)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(21.dp))
                .background(AegisBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(22.dp),
            )
        }
        Column(
            modifier = Modifier.padding(start = 14.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = AegisText,
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = AegisText2,
            )
        }
    }
}