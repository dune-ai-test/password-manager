package com.aegis.manager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisOutline
import com.aegis.manager.ui.theme.AegisPrimary
import com.aegis.manager.ui.theme.AegisSurface2
import com.aegis.manager.ui.theme.AegisText
import com.aegis.manager.ui.theme.AegisText2
import com.aegis.manager.ui.theme.JetBrainsMonoFamily

/**
 * The dark outlined field used by every form in the app.
 *
 * [mono] renders the value in JetBrains Mono, which the design uses for
 * passwords and URLs.
 */
@Composable
fun AegisTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    mono: Boolean = false,
    singleLine: Boolean = true,
    minHeight: Int = 52,
) {
    var revealed by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(minHeight.dp.coerceAtLeast(24.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(AegisSurface2)
            .border(1.dp, AegisOutline, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
    ) {
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AegisText2,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = LocalTextStyle.current.merge(
                    TextStyle(
                        color = AegisText,
                        fontFamily = if (mono) JetBrainsMonoFamily else MaterialTheme.typography.bodyMedium.fontFamily,
                    ),
                ),
                cursorBrush = SolidColor(AegisText),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = if (isPassword && !revealed) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (isPassword) {
            IconButton(onClick = { revealed = !revealed }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = if (revealed) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (revealed) "Hide password" else "Show password",
                    tint = AegisText2,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

/** Rounded search input with the leading magnifier from the design. */
@Composable
fun AegisSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(AegisSurface2)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = AegisText2,
            modifier = Modifier.size(20.dp),
        )
        Box(Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AegisText2,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.merge(TextStyle(color = AegisText)),
                cursorBrush = SolidColor(AegisText),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (trailing != null) {
            Box(Modifier.width(8.dp))
            trailing()
        }
    }
}

/** Multi-line notes field. */
@Composable
fun AegisNotesField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AegisSurface2)
            .border(1.dp, AegisOutline, RoundedCornerShape(14.dp))
            .padding(16.dp),
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = AegisText2,
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.merge(TextStyle(color = AegisText)),
            cursorBrush = SolidColor(AegisText),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/** Section heading above grouped rows. */
@Composable
fun AegisSectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = AegisText2,
        modifier = modifier.padding(start = 4.dp, bottom = 8.dp),
    )
}

/** Full-width container used for top bars. */
@Composable
fun AegisTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigateBack: (() -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(AegisBg),
        contentAlignment = Alignment.Center,
    ) {
        if (onNavigateBack != null) {
            AegisLinkButton(text = "‹", onClick = onNavigateBack, modifier = Modifier.align(Alignment.CenterStart))
        } else if (leading != null) {
            Box(Modifier.align(Alignment.CenterStart)) { leading() }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = AegisText,
            modifier = Modifier.padding(horizontal = 72.dp),
            maxLines = 1,
        )
        if (trailing != null) {
            Box(Modifier.align(Alignment.CenterEnd)) { trailing() }
        }
    }
}

/** Simple on/off switch styled for the dark theme. */
@Composable
fun AegisToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(46.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (checked) AegisPrimary else AegisOutline)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Box(
            Modifier
                .padding(horizontal = 3.dp)
                .size(22.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(AegisBg),
        )
    }
}