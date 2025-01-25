package com.yapp.feature.signup.signup.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.feature.signup.R

@Composable
fun NameContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.signup_screen_step_1),
            style = YappTheme.typography.caption2Bold,
            color = YappTheme.colorScheme.primaryNormal,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_name_title),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_name_description),
            style = YappTheme.typography.body2NormalMedium,
            color = YappTheme.colorScheme.labelAlternative,
        )

        Spacer(Modifier.height(40.dp))

        YappInputTextLarge(
            label = stringResource(R.string.signup_screen_name_input_text_label),
            placeholder = stringResource(R.string.signup_screen_name_input_text_placeholder),
            value = "",
            onValueChange = {},
        )
    }
}

@Preview
@Composable
fun NameContentPreview() {
    YappTheme {
        YappBackground {
            NameContent()
        }
    }
}