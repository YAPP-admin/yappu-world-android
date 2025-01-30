package com.yapp.feature.signup.signup.page.position.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.signup.R

@Composable
fun ActivityUnitInputSection(
    generation: String?,
    position: String?,
    dropdownOptions: List<String>,
    onGenerationChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
    onDropdownMenuShown: () -> Unit,
) {
    Row {
        YappInputTextLarge(
            modifier = Modifier.width(120.dp),
            label = stringResource(R.string.signup_screen_position_generation_input_text_label),
            placeholder = stringResource(R.string.signup_screen_position_generation_input_text_placeholder),
            value = generation ?: "",
            onValueChange = onGenerationChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.width(16.dp))

        PositionDropdown(
            label = stringResource(R.string.signup_screen_position_position_input_text_label),
            value = position,
            onValueChange = onPositionChange,
            placeholder = stringResource(R.string.signup_screen_position_position_input_text_placeholder),
            dropdownOptions = dropdownOptions,
            onDropdownMenuShown = onDropdownMenuShown,
        )
    }
}

@Composable
fun PreviousActivityUnitInputSection(
    index: Int,
    generation: String?,
    position: String?,
    dropdownOptions: List<String>,
    onGenerationChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
    onDeleteButtonClick: (Int) -> Unit,
    onDropdownMenuShown: () -> Unit,
) {
    val displayIndex = index + 1

    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = YappTheme.colorScheme.lineNormalNormal,
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.signup_screen_position_previous_generation, displayIndex),
                color = YappTheme.colorScheme.labelNormal,
                style = YappTheme.typography.body1NormalBold
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .yappClickable {
                        onDeleteButtonClick(index)
                    },
                painter = painterResource(R.drawable.icon_trash_bin),
                contentDescription = stringResource(
                    R.string.signup_screen_position_previous_delete_content_description,
                    displayIndex
                ),
            )
        }

        Spacer(Modifier.height(8.dp))

        ActivityUnitInputSection(
            generation = generation,
            position = position,
            dropdownOptions = dropdownOptions,
            onGenerationChange = onGenerationChange,
            onPositionChange = onPositionChange,
            onDropdownMenuShown = onDropdownMenuShown,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityUnitInputSectionPreview() {
    YappTheme {
        ActivityUnitInputSection(
            generation = "12",
            position = "Android",
            dropdownOptions = listOf("PM", "Designer", "Android"),
            onGenerationChange = {},
            onPositionChange = {},
            onDropdownMenuShown = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviousActivityUnitInputSectionPreview() {
    YappTheme {
        PreviousActivityUnitInputSection(
            index = 0,
            generation = "12",
            position = "Android",
            dropdownOptions = listOf("PM", "Designer", "Android"),
            onGenerationChange = {},
            onPositionChange = {},
            onDeleteButtonClick = {},
            onDropdownMenuShown = {},
        )
    }
}
