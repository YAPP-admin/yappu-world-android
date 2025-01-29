package com.yapp.feature.signup.signup.page.position

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.signup.R
import com.yapp.feature.signup.signup.page.position.component.ActivityUnitInputSection
import com.yapp.feature.signup.signup.page.position.component.PreviousActivityUnitInputSection
import com.yapp.model.ActivityUnit

@Composable
fun PositionPage(
    viewModel: PositionViewModel = hiltViewModel(),
    name: String,
    onActivityUnitsChanged: (List<ActivityUnit>) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            is PositionSideEffect.ActivityUnitsChanged -> onActivityUnitsChanged(it.activityUnits)
            PositionSideEffect.ClearFocus -> focusManager.clearFocus(force = true)
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
                    onGenerationChange = { onIntent(PositionIntent.ChangeGeneration(it)) },
                    onPositionChange = { onIntent(PositionIntent.ChangePosition(it)) },
                    onDropdownMenuShown = { onIntent(PositionIntent.DropdownMenuShown) },
                )

                uiState.previousActivityUnit.forEachIndexed { index, activityUnit ->
                    Spacer(Modifier.height(24.dp))

                    PreviousActivityUnitInputSection(
                        index = index,
                        generation = activityUnit.generation?.toString() ?: "",
                        position = activityUnit.position ?: "",
                        onGenerationChange = { onIntent(PositionIntent.ChangePreviousGeneration(index, it)) },
                        onPositionChange = { onIntent(PositionIntent.ChangePreviousPosition(index, it)) },
                        onDeleteButtonClick = { onIntent(PositionIntent.ClickDeletePreviousGenerationButton(it)) },
                        onDropdownMenuShown = { onIntent(PositionIntent.DropdownMenuShown) },
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

@Preview
@Composable
private fun PositionContentPreview() {
    YappTheme {
        YappBackground {
            PositionContent()
        }
    }
}