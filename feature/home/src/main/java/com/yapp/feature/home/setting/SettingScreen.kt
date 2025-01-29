package com.yapp.feature.home.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonXLarge
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.component.control.switches.YappSwitchMedium
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.component.header.YappHeaderActionbarExpanded
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.borderBottom
import com.yapp.feature.home.R


@Composable
fun SettingScreen(
) {
    YappBackground {
        Column {
            YappHeaderActionbarExpanded(
                title = stringResource(R.string.setting_screen_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                contentDescription = stringResource(R.string.setting_screen_back_icon_content_description),
                onClickLeftIcon = {},
            )

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                SettingItemLarge(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(R.string.setting_screen_item_notification),
                    slot = {
                        YappSwitchMedium(
                            checked = true,
                            onCheckedChange = {}
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.setting_screen_item_terms),
                    color = YappTheme.colorScheme.labelNeutral,
                    style = YappTheme.typography.headline1Bold
                )

                SettingItemMedium(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(R.string.setting_screen_item_privacy_policy),
                    onClick = {},
                    slot = {
                        Icon(
                            painter = painterResource(com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemMedium(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(R.string.setting_screen_item_terms),
                    onClick = {},
                    slot = {
                        Icon(
                            painter = painterResource(com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemLarge(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(R.string.setting_screen_item_inquiry),
                    onClick = {},
                    slot = {
                        Icon(
                            painter = painterResource(com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemLarge(
                    text = stringResource(R.string.setting_screen_item_delect_account),
                    onClick = {}
                )
            }

            Spacer(Modifier.weight(1f))

            YappOutlinedSecondaryButtonXLarge(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.setting_screen_item_logout),
                onClick = {}
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingItemLarge(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    slot: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .yappClickable(
                runIf = onClick != null,
                onClick = onClick
            )
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = YappTheme.colorScheme.labelNeutral,
            style = YappTheme.typography.headline1Bold
        )

        slot?.invoke()
    }
}

@Composable
fun SettingItemMedium(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    slot: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .yappClickable(
                runIf = onClick != null,
                onClick = onClick
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = YappTheme.colorScheme.labelNeutral,
            style = YappTheme.typography.body1NormalMedium
        )

        slot?.invoke()
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    YappTheme {
        SettingScreen()
    }
}