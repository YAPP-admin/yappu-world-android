package com.yapp.core.designsystem.component.input.nestedcheckbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappNestedCheckboxBasic(
    modifier: Modifier,
    checked: Boolean,
    text: String,
    textStyle: TextStyle,
    iconSize: Dp,
    iconRightSpacing: Dp,
    colors: NestedCheckboxColors,
    onCheckedChange: ((Boolean) -> Unit),
) {
    val iconColor = colors.iconColor(checked = checked)
    val textColor = colors.textColor(checked = checked)

    Row(
        modifier = modifier
            .yappClickable(
                onClick = { onCheckedChange(!checked) },
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(R.drawable.icon_check),
            tint = iconColor,
            contentDescription = if (checked) "선택됨" else "선택되지 않음"
        )

        Spacer(Modifier.width(iconRightSpacing))

        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}

@Composable
fun YappNestedCheckboxNormal(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    textStyle: TextStyle = NestedCheckboxDefaults.textStyleNormal,
    iconSize: Dp = NestedCheckboxDefaults.iconSizeNormal,
    iconRightSpacing: Dp = NestedCheckboxDefaults.iconRightSpacingNormal,
    colors: NestedCheckboxColors = NestedCheckboxDefaults.colors,
    onCheckedChange: ((Boolean) -> Unit),
) {
    YappNestedCheckboxBasic(
        modifier = modifier,
        checked = checked,
        text = text,
        textStyle = textStyle,
        iconSize = iconSize,
        iconRightSpacing = iconRightSpacing,
        colors = colors,
        onCheckedChange = onCheckedChange,
    )
}

@Composable
fun YappNestedCheckboxSmall(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    textStyle: TextStyle = NestedCheckboxDefaults.textStyleSmall,
    iconSize: Dp = NestedCheckboxDefaults.iconSizeSmall,
    iconRightSpacing: Dp = NestedCheckboxDefaults.iconRightSpacingSmall,
    colors: NestedCheckboxColors = NestedCheckboxDefaults.colors,
    onCheckedChange: ((Boolean) -> Unit),
) {
    YappNestedCheckboxBasic(
        modifier = modifier,
        checked = checked,
        text = text,
        textStyle = textStyle,
        iconSize = iconSize,
        iconRightSpacing = iconRightSpacing,
        colors = colors,
        onCheckedChange = onCheckedChange,
    )
}

@Preview(showBackground = true)
@Composable
private fun YappNestedCheckboxNormalPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappNestedCheckboxNormal(
                checked = true,
                text = "텍스트",
                onCheckedChange = { }
            )

            YappNestedCheckboxNormal(
                checked = false,
                text = "텍스트",
                onCheckedChange = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappNestedCheckboxSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappNestedCheckboxSmall(
                checked = true,
                text = "텍스트",
                onCheckedChange = { }
            )

            YappNestedCheckboxSmall(
                checked = false,
                text = "텍스트",
                onCheckedChange = { }
            )
        }
    }
}