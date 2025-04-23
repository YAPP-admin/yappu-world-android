package com.yapp.core.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.input.text.InputTextDefaults
import com.yapp.core.designsystem.component.input.text.YappBasicInputText
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.R

@Composable
fun PasswordInputTextLarge(
    modifier: Modifier = Modifier,
    label: String? = null,
    password: String,
    onPasswordChange: (String) -> Unit,
    placeholder: String = "",
    description: String? = null,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    var isPasswordVisible by remember { mutableStateOf(false) }

    YappBasicInputText(
        modifier = modifier,
        label = label,
        value = password,
        onValueChange = onPasswordChange,
        placeholder = placeholder,
        description = description,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = InputTextDefaults.shapeLarge,
        colors = InputTextDefaults.colors,
        textStyles = InputTextDefaults.textStylesLarge,
        spacings = InputTextDefaults.spacingsLarge,
        contentPaddings = InputTextDefaults.contentPaddingsLarge,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        rightIcon = {
            Icon(
                modifier = Modifier
                    .size(InputTextDefaults.rightIconSizeLarge)
                    .clip(CircleShape)
                    .yappClickable { isPasswordVisible = !isPasswordVisible },
                painter = painterResource(
                    if (isPasswordVisible) {
                        R.drawable.icon_eye
                    } else {
                        R.drawable.icon_eye_slash
                    }
                ),
                tint = if (isPasswordVisible) {
                    YappTheme.colorScheme.labelAlternative
                } else {
                    YappTheme.colorScheme.labelAssistive
                },
                contentDescription = "비밀번호 ${if (isPasswordVisible) "숨김" else "가리기"} 버튼"
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextLargePasswordPreview() {
    YappTheme {
        Column {
            var defaultInputValue by remember { mutableStateOf("test1234") }
            var errorInputText by remember { mutableStateOf("test1234") }

            PasswordInputTextLarge(
                label = "Label",
                password = defaultInputValue,
                onPasswordChange = { defaultInputValue = it },
                placeholder = "Placeholder",
                description = "Description",
            )

            Spacer(Modifier.height(10.dp))

            PasswordInputTextLarge(
                label = "Label",
                password = errorInputText,
                onPasswordChange = { errorInputText = it },
                placeholder = "Placeholder",
                isError = true,
                description = "Description",
            )
        }
    }
}