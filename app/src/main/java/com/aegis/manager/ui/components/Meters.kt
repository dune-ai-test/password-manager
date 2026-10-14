package com.aegis.manager.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aegis.manager.domain.Strength
import com.aegis.manager.ui.theme.AegisOrange
import com.aegis.manager.ui.theme.AegisPurple
import com.aegis.manager.ui.theme.AegisRed
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2

fun Strength.color(): Color = when (this) {
    Strength.WEAK -> AegisRed
    Strength.FAIR -> AegisOrange
    Strength.GOOD -> AegisPurple
    Strength.STRONG -> com.aegis.manager.ui.theme.AegisPrimary
    Strength.EXCELLENT -> com.aegis.manager.ui.theme.AegisPrimary
}

fun Strength.label(): String = when (this) {
    Strength.WEAK -> "Weak"
    Strength.FAIR -> "Fair"
    Strength.GOOD -> "Good"
    Strength.STRONG -> "Strong"
    Strength.EXCELLENT -> "Excellent"
}

/** Segmented strength bar used on the master-password and generator screens. */
@Composable
fun AegisStrengthMeter(
    strength: Strength,
    modifier: Modifier = Modifier,
) {
    val color = strength.color()
    val filled = when (strength) {
        Strength.WEAK -> 1
        Strength.FAIR -> 2
        Strength.GOOD -> 3
        Strength.STRONG -> 4
        Strength.EXCELLENT -> 5
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        repeat(5) { index ->
            Box(
                Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(if (index < filled) color else AegisSurface2),
            )
        }
    }
}

/** Labelled strength row: caption on the left, verdict on the right. */
@Composable
fun AegisStrengthRow(
    label: String,
    strength: Strength,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = AegisText2,
            )
            Text(
                text = strength.label(),
                style = MaterialTheme.typography.labelMedium,
                color = strength.color(),
            )
        }
        Box(Modifier.height(8.dp))
        AegisStrengthMeter(strength = strength)
    }
}

/** Circular security score used at the top of the Security screen. */
@Composable
fun AegisScoreRing(
    score: Int,
    label: String,
    modifier: Modifier = Modifier,
    color: Color = com.aegis.manager.ui.theme.AegisPrimary,
) {
    Box(
        modifier = modifier.size(148.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.size(148.dp)) {
            val stroke = 13.dp.toPx()
            val inset = stroke / 2
            val arcSize = androidx.compose.ui.geometry.Size(
                size.width - stroke,
                size.height - stroke,
            )
            drawArc(
                color = AegisSurface2,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )
            drawArc(
                color = color,
                startAngle = 135f,
                sweepAngle = 270f * (score.coerceIn(0, 100) / 100f),
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.displaySmall,
                color = AegisText,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = color,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/** Numbered statistic chip used under the security score. */
@Composable
fun AegisStatTile(
    count: Int,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
            .background(AegisSurface2)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = color,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = AegisText2,
            textAlign = TextAlign.Center,
        )
    }
}