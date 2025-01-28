package com.yapp.feature.login.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.component.input.nestedcheckbox.YappNestedCheckboxNormal
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.BottomDialog
import com.yapp.feature.login.R


@Composable
fun AgreementBottomDialog(
    onDismiss: () -> Unit,
    terms: Boolean,
    personalPolicy: Boolean,
    onTermsChecked: (Boolean) -> Unit,
    onPersonalPolicyChecked: (Boolean) -> Unit,
    enableNextButton: Boolean,
    onNextButtonClick: () -> Unit,
) {
    BottomDialog(
        onDismiss = onDismiss
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.agreement_bottom_dialog_title),
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.labelNormal
            )
            Spacer(Modifier.height(24.dp))
            AgreementContent(
                text = stringResource(R.string.agreement_bottom_dialog_terms),
                checked = terms,
                onCheckedChange = { onTermsChecked(it) }
            )
            AgreementContent(
                text = stringResource(R.string.agreement_bottom_dialog_personal_policy),
                checked = personalPolicy,
                onCheckedChange = { onPersonalPolicyChecked(it) }
            )
            Spacer(Modifier.height(24.dp))
            YappSolidPrimaryButtonXLarge(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.agreement_bottom_dialog_button_next),
                enable = enableNextButton,
                onClick = { onNextButtonClick() }
            )
        }
    }
}

@Composable
fun AgreementContent(
    text: String,
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YappNestedCheckboxNormal(
            text = text,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.agreement_bottom_dialog_text_essential),
            style = YappTheme.typography.body2NormalRegular,
            color = YappTheme.colorScheme.primaryNormal,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            YappTextAssistiveButtonSmall(
                text = stringResource(R.string.agreement_bottom_dialog_button_detail),
                onClick = {}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AgreementBottomDialogPreview() {
    YappTheme {
        AgreementBottomDialog(
            {},
            terms = true,
            personalPolicy = false,
            onTermsChecked = {},
            onPersonalPolicyChecked = {},
            enableNextButton = false,
            onNextButtonClick = {}
        )
    }
}