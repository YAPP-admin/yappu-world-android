package com.yapp.feature.signup.signup.page.position

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.signup.R
import com.yapp.feature.signup.signup.component.PositionDropdown
import com.yapp.feature.signup.signup.component.SignUpCodeBottomDialog
import com.yapp.model.ActivityUnit

@Composable
fun PositionPage(
    viewModel: PositionViewModel = hiltViewModel(),
    name: String,
    onActivityUnitsChanged: (List<ActivityUnit>) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            is PositionSideEffect.ActivityUnitsChanged -> onActivityUnitsChanged(it.activityUnits)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(PositionIntent.EnterScreen(name))
    }

    PositionContent(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun PositionContent(
    uiState: PositionState = PositionState(),
    onIntent: (PositionIntent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.signup_screen_step_4),
            style = YappTheme.typography.caption2Bold,
            color = YappTheme.colorScheme.primaryNormal,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_position_title, uiState.name),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_position_description),
            style = YappTheme.typography.body2NormalMedium,
            color = YappTheme.colorScheme.labelAlternative,
        )

        val gradientHeight = 40.dp

        Box {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(gradientHeight))

                ActivityUnitInputSection(
                    generation = uiState.currentActivityUnit.generation?.toString() ?: "",
                    position = uiState.currentActivityUnit.position ?: "",
                    onGenerationChange = { onIntent(PositionIntent.GenerationChange(it)) },
                    onPositionChange = { onIntent(PositionIntent.PositionChange(it)) },
                )

                uiState.previousActivityUnit.forEachIndexed { index, activityUnit ->
                    Spacer(Modifier.height(24.dp))

                    PreviousActivityUnitInputSection(
                        index = index,
                        generation = activityUnit.generation?.toString() ?: "",
                        position = activityUnit.position ?: "",
                        onGenerationChange = { onIntent(PositionIntent.PreviousGenerationChange(index, it)) },
                        onPositionChange = { onIntent(PositionIntent.PreviousPositionChange(index, it)) },
                        onDeleteButtonClick = { onIntent(PositionIntent.ClickDeletePreviousGenerationButton(it)) },
                    )
                }

                Spacer(Modifier.height(24.dp))

                YappTextAssistiveButtonSmall(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.signup_screen_add_previous_generation_button),
                    leftIcon = {
                        Icon(
                            painter = painterResource(com.yapp.core.designsystem.R.drawable.icon_plus),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAlternative,
                        )
                    },
                    onClick = { onIntent(PositionIntent.ClickAddPreviousGenerationButton) }
                )
            }

            GradientBottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gradientHeight),
                color = YappTheme.colorScheme.backgroundNormalNormal
            )
        }
    }
}

@Composable
private fun PreviousActivityUnitInputSection(
    index: Int,
    generation: String?,
    position: String?,
    onGenerationChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
    onDeleteButtonClick: (Int) -> Unit,
) {
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
                stringResource(R.string.signup_screen_position_previous_generation, index + 1),
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
                contentDescription = null,
            )
        }

        Spacer(Modifier.height(8.dp))

        ActivityUnitInputSection(
            generation = generation,
            position = position,
            onGenerationChange = onGenerationChange,
            onPositionChange = onPositionChange
        )
    }
}

@Composable
private fun ActivityUnitInputSection(
    generation: String?,
    position: String?,
    onGenerationChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
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
            // TODO 임시 데이터, 서버에서 받아오게 변경 필요
            dropdownOptions = listOf("PM", "UX/UI Design", "Android", "iOS", "Web", "Server"),
        )
    }
}

@Preview
@Composable
private fun PositionContentPreview() {
    YappTheme {
        YappBackground {
            PositionContent()
        }
    }
}