package com.yapp.core.designsystem.component.input.text

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappBasicInputText(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    description: String? = null,
    isError: Boolean = false,
    rightIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape,
    colors: InputTextColors,
    textStyles: InputTextTextStyles,
    spacings: InputTextSpacings,
    contentPaddings: PaddingValues,
) {
    val focused = interactionSource.collectIsFocusedAsState().value

    val labelColor = colors.labelColor

    val inputTextColor = colors.inputTextColor(
        value = value,
        isError = isError,
        focused = focused,
    )

    val outlineColor = colors.outlineColor(
        value = value,
        isError = isError,
        focused = focused,
    )

    val descriptionColor = colors.descriptionColor(
        value = value,
        isError = isError,
        focused = focused,
    )

    val yappTextSelectionColors = TextSelectionColors(
        handleColor = YappTheme.colorScheme.primaryNormal, // 물방울 모양 핸들의 색상
        backgroundColor = YappTheme.colorScheme.primaryNormal // 선택된 텍스트의 배경색
    )

    CompositionLocalProvider(LocalTextSelectionColors provides yappTextSelectionColors) {
        Column(
            modifier = modifier,
        ) {
            if (label != null) {
                Text(
                    text = label,
                    color = labelColor,
                    style = textStyles.labelTextStyle
                )

                Spacer(modifier = Modifier.height(spacings.labelBottomSpacing))
            }

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = outlineColor,
                        shape = shape,
                    )
                    .padding(contentPaddings),
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyles.inputTextTextStyle.copy(color = inputTextColor),
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(value = YappTheme.colorScheme.primaryNormal),
            ) { innerTextField ->
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyles.inputTextTextStyle,
                                color = colors.defaultInputTextColor,
                            )
                        }
                        innerTextField()
                    }

                    if (rightIcon != null) {
                        Spacer(Modifier.width(spacings.rightIconStartSpacing))
                        rightIcon()
                    }
                }

            }

            if (description != null) {
                Spacer(modifier = Modifier.height(spacings.descriptionTopSpacing))

                Text(
                    text = description,
                    color = descriptionColor,
                    style = textStyles.descriptionTextStyle,
                )
            }
        }
    }
}

@Composable
fun YappInputTextLarge(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    description: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = InputTextDefaults.shapeLarge,
    colors: InputTextColors = InputTextDefaults.colors,
    textStyles: InputTextTextStyles = InputTextDefaults.textStylesLarge,
    spacings: InputTextSpacings = InputTextDefaults.spacingsLarge,
    contentPaddings: PaddingValues = InputTextDefaults.contentPaddingsLarge,
) {
    YappBasicInputText(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        description = description,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        textStyles = textStyles,
        spacings = spacings,
        contentPaddings = contentPaddings,
    )
}

@Composable
fun YappInputTextLarge(
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = InputTextDefaults.shapeLarge,
    colors: InputTextColors = InputTextDefaults.colors,
    textStyles: InputTextTextStyles = InputTextDefaults.textStylesLarge,
    spacings: InputTextSpacings = InputTextDefaults.spacingsLarge,
    contentPaddings: PaddingValues = InputTextDefaults.contentPaddingsLarge,
) {
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
        shape = shape,
        colors = colors,
        textStyles = textStyles,
        spacings = spacings,
        contentPaddings = contentPaddings,
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
private fun YappInputTextLargePreview() {
    YappTheme {
        Column {
            var defaultInputValue by remember { mutableStateOf("") }
            var errorInputText by remember { mutableStateOf("") }

            YappInputTextLarge(
                label = "Label",
                value = defaultInputValue,
                onValueChange = { defaultInputValue = it },
                placeholder = "Placeholder",
                description = "Description"
            )

            Spacer(Modifier.height(10.dp))

            YappInputTextLarge(
                label = "Label",
                value = errorInputText,
                onValueChange = { errorInputText = it },
                placeholder = "Placeholder",
                isError = true,
                description = "Description"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappInputTextLargePasswordPreview() {
    YappTheme {
        Column {
            var defaultInputValue by remember { mutableStateOf("test1234") }
            var errorInputText by remember { mutableStateOf("test1234") }

            YappInputTextLarge(
                label = "Label",
                password = defaultInputValue,
                onPasswordChange = { defaultInputValue = it },
                placeholder = "Placeholder",
                description = "Description",
            )

            Spacer(Modifier.height(10.dp))

            YappInputTextLarge(
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