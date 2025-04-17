package com.yapp.feature.profile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable

@Composable
internal fun ProfileTopBarSection(
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = "마이페이지")
        Icon(
            modifier = Modifier.yappClickable(onClick = onClickSettings),
            painter = painterResource(R.drawable.icon_setting),
            contentDescription = "setting"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileTopBarSectionPreview() {
    ProfileTopBarSection { }
}
