package com.yapp.feature.signup.signup.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.feature.signup.R
import com.yapp.feature.signup.signup.component.PositionDropdown
import com.yapp.feature.signup.signup.component.SignUpCodeBottomDialog

@Composable
fun PositionContent() {
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
            text = stringResource(R.string.signup_screen_position_title, "박상윤"),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_position_description),
            style = YappTheme.typography.body2NormalMedium,
            color = YappTheme.colorScheme.labelAlternative,
        )

        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            YappInputTextLarge(
                modifier = Modifier.width(120.dp),
                label = stringResource(R.string.signup_screen_position_generation_input_text_label),
                placeholder = stringResource(R.string.signup_screen_position_generation_input_text_placeholder),
                value = "",
                onValueChange = {},
            )

            Spacer(Modifier.width(16.dp))

            var selectedValue by remember { mutableStateOf<String?>(null) }

            PositionDropdown(
                label = stringResource(R.string.signup_screen_position_position_input_text_label),
                value = selectedValue,
                onValueChange = { selectedValue = it },
                placeholder = stringResource(R.string.signup_screen_position_position_input_text_placeholder),
                // TODO 임시 데이터, 서버에서 받아오게 변경 필요
                dropdownOptions = listOf("PM", "UX/UI Design", "Android", "iOS", "Web", "Server"),
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
            onClick = {}
        )

        SignUpCodeBottomDialog()
    }
}

@Preview
@Composable
fun PositionContentPreview() {
    YappTheme {
        YappBackground {
            PositionContent()
        }
    }
}