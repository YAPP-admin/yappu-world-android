package com.yapp.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.RoleChip
import com.yapp.core.ui.component.UserRole

@Composable
fun ProfileSection(
    name: String,
    activityStatus: UserRole,
    generation: Int,
    position: String,
) {
    HomeSectionBackground {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                color = YappTheme.colorScheme.labelNormal,
                style = YappTheme.typography.title2Bold
            )
            Spacer(Modifier.width(8.dp))
            RoleChip(role = activityStatus)
        }
        Spacer(Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${generation}기",
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.label1NormalMedium
            )
            Text(
                text = "∙",
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.label1NormalMedium
            )
            Text(
                text = position,
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.label1NormalMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSectionPreview() {
    YappTheme {
        ProfileSection(
            name = "홍길동",
            activityStatus = UserRole.ACTIVE,
            generation = 1,
            position = "안드로이드"
        )
    }
}
