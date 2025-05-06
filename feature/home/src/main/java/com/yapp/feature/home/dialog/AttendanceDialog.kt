package com.yapp.feature.home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.button.text.YappTextPrimaryButtonSmall
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.BottomDialog
import com.yapp.core.ui.component.YappBackground
import com.yapp.feature.home.R

@Composable
internal fun AttendanceDialog(
    code: List<String>,
    inputCompleteButtonEnabled: Boolean,
    isCodeInputTextError: Boolean,
    onCodeChange: (List<String>) -> Unit,
    onDismissRequest: () -> Unit,
    clickAttendanceButton: () -> Unit
) {
    val codeLength = 4
    val focusRequesters = remember { List(codeLength) { FocusRequester() } }

    BottomDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(
                        color = YappTheme.colorScheme.staticWhite,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = stringResource(R.string.attendance_dialog_title),
                    style = YappTheme.typography.headline1Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.attendance_dialog_message),
                    style = YappTheme.typography.label1ReadingRegular
                )
                Spacer(Modifier.height(24.dp))

                PinCodeInput(
                    values = code,
                    onValueChange = { index, new ->
                        onCodeChange(code.toMutableList().apply { this[index] = new })
                    },
                    focusRequesters = focusRequesters,
                    isAllValuesEntered = code.all { it.isNotEmpty() },
                    isError = isCodeInputTextError
                )

                if (isCodeInputTextError) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.attendance_dialog_code_not_correct),
                        style = YappTheme.typography.label2Regular,
                        color = YappTheme.colorScheme.statusNegative
                    )
                }

                Spacer(Modifier.height(24.dp))

                YappSolidPrimaryButtonLarge(
                    modifier = Modifier.fillMaxWidth(),
                    enable = inputCompleteButtonEnabled,
                    onClick = clickAttendanceButton,
                    text = stringResource(R.string.attendance_dialog_confirm_button)
                )

                Spacer(Modifier.height(8.dp))

                YappTextPrimaryButtonSmall(
                    modifier = Modifier.fillMaxWidth(),
                    enable = true,
                    text = stringResource(R.string.attendance_dialog_cancel_button),
                    onClick = onDismissRequest
                )
            }
        }
    )
}

@Composable
private fun PinCodeInput(
    values: List<String>,
    onValueChange: (Int, String) -> Unit,
    focusRequesters: List<FocusRequester>,
    isAllValuesEntered: Boolean,
    isError: Boolean
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isAllValuesEntered) {
        if (isAllValuesEntered) {
            focusManager.clearFocus()
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        values.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { input ->
                    if (input.length <= 1 && input.all { it.isDigit() }) {
                        onValueChange(index, input)
                        if (input.isNotEmpty()) {
                            if (index < values.lastIndex) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(60.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent {
                        if (it.key == Key.Backspace && it.type == KeyEventType.KeyDown && values[index].isEmpty()) {
                            if (index > 0) {
                                onValueChange(index - 1, "")
                                focusRequesters[index - 1].requestFocus()
                            }
                            true
                        } else false
                    },
                singleLine = true,
                textStyle = YappTheme.typography.body1NormalRegular.copy(
                    textAlign = TextAlign.Center,
                    color = if (isError) YappTheme.colorScheme.statusNegative else YappTheme.colorScheme.labelNormal
                ),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isError) {
                        YappTheme.colorScheme.statusNegative
                    } else {
                        YappTheme.colorScheme.primaryNormal
                    },
                    unfocusedBorderColor = if (isError) {
                        YappTheme.colorScheme.statusNegative
                    } else {
                        YappTheme.colorScheme.labelNormal
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AttendanceDialogPreview() {
    YappBackground {
        AttendanceDialog(
            code = listOf("1", "2", "3", "4"),
            inputCompleteButtonEnabled = true,
            isCodeInputTextError = false,
            onCodeChange = {},
            onDismissRequest = {},
            clickAttendanceButton = {}
        )
    }
}
