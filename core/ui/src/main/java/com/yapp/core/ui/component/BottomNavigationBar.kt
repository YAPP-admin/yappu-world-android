package com.yapp.core.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    @StringRes iconTextId: Int,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    selectedContentColor: Color,
    unselectedContentColor: Color,
    selectedBackgroundColor: Color,
    unselectedBackgroundColor: Color,
    onClick: () -> Unit
) {
    val contentColor = if (selected) selectedContentColor else unselectedContentColor
    val backgroundColor = if (selected) selectedBackgroundColor else unselectedBackgroundColor

    val icon = if (selected) selectedIcon else unselectedIcon

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 6.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = iconTextId),
                tint = contentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = iconTextId),
                style = YappTheme.typography.caption2Bold,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarItemPreview() {
    YappTheme {
        var isSelected by remember { mutableStateOf(false) }

        BottomNavigationBarItem(
            selected = isSelected,
            iconTextId = com.yapp.core.designsystem.R.string.icon_home,
            selectedIcon = com.yapp.core.designsystem.R.drawable.icon_home_selected,
            unselectedIcon = com.yapp.core.designsystem.R.drawable.icon_home_unselected,
            selectedContentColor = YappTheme.colorScheme.primaryNormal,
            unselectedContentColor = YappTheme.colorScheme.labelAssistive,
            selectedBackgroundColor = Color(0xFFFFF8F5),
            unselectedBackgroundColor = YappTheme.colorScheme.staticWhite
        ) {
            isSelected = !isSelected
        }
    }
}