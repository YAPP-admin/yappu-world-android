package com.yapp.feature.signup.signup.page.position.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.input.text.InputTextDefaults
import com.yapp.core.designsystem.component.input.text.YappBasicInputText
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.signup.R

@Composable
fun PositionDropdown(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String?,
    onValueChange: (String) -> Unit,
    placeholder: String,
    dropdownOptions: List<String>,
    description: String? = null,
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    @Suppress("NAME_SHADOWING")
    val value = if (isDropdownExpanded.not() && value == null) {
        ""
    } else {
        value
    }

    var boxWidth by remember { mutableIntStateOf(0) }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier.onSizeChanged { boxWidth = it.width }
        ) {
            YappBasicInputText(
                label = label,
                value = value ?: placeholder,
                focused = isDropdownExpanded,
                onValueChange = {}, // Disable direct text input
                placeholder = placeholder,
                description = description,
                rightIcon = {
                    val icon = if (isDropdownExpanded) {
                        R.drawable.icon_dropdown_arrow_up
                    } else {
                        R.drawable.icon_dropdown_arrow_down
                    }

                    val tint = if (isDropdownExpanded.not() && value.isNullOrEmpty()) {
                        YappTheme.colorScheme.labelNeutral.copy(alpha = 0.16f)
                    } else {
                        YappTheme.colorScheme.labelNormal
                    }

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = tint,
                    )
                },
                shape = InputTextDefaults.shapeLarge,
                colors = InputTextDefaults.colors,
                textStyles = InputTextDefaults.textStylesLarge,
                spacings = InputTextDefaults.spacingsLarge,
                contentPaddings = InputTextDefaults.contentPaddingsLarge,
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .yappClickable(
                        rippleEnabled = false,
                        onClick = { isDropdownExpanded = true }
                    )
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(
                    color = YappTheme.colorScheme.backgroundElevatedNormal,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(1.dp, YappTheme.colorScheme.lineNormalNormal, RoundedCornerShape(12.dp))
                .width(with(LocalDensity.current) { boxWidth.toDp() })
                .padding(8.dp),
            offset = DpOffset(y = 4.dp, x = 0.dp),
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            containerColor = Color.Transparent,
            shape = RoundedCornerShape(12.dp),
        ) {
            dropdownOptions.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    onClick = {
                        onValueChange(option)
                        isDropdownExpanded = false
                    },
                    contentPadding = PaddingValues(8.dp),
                    text = {
                        Text(
                            text = option,
                            style = YappTheme.typography.body1NormalRegular,
                            color = YappTheme.colorScheme.labelNormal
                        )
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PositionDropdownPreview() {
    YappTheme {
        var selectedValue by remember { mutableStateOf<String?>(null) }

        PositionDropdown(
            value = selectedValue,
            onValueChange = { selectedValue = it },
            placeholder = "선택해주세요",
            dropdownOptions = listOf("PM", "UX/UI Design", "Android", "iOS", "Web", "Server"),
        )
    }
}
