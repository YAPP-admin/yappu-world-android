package com.yapp.feature.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun ProfileInformationSection(
    modifier: Modifier = Modifier,
    userName: String,
    userGeneration: String,
    userPosition: String,
    userRole: String
) {
    Row(
        modifier = modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(coreDesignR.drawable.yappo_profile), contentDescription = null)
        Column {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = userName, style = YappTheme.typography.title2Bold)
                YappChipSmall(text = userRole, colorType = ChipColorType.Main, isFill = true)
            }
            Text("${userGeneration}, $userPosition")
        }
    }
}
