package com.yapp.core.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    borderWidth: Dp = 1.dp,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0xFFF5F5F5),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = borderWidth.toPx()
                )
            },
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 4.dp
                )
                .selectableGroup(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }
}

@Composable
fun BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    @StringRes iconTextId: Int,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    selectedContentColor: Color = YappTheme.colorScheme.primaryNormal,
    unselectedContentColor: Color = YappTheme.colorScheme.labelAssistive,
    selectedBackgroundColor: Color = Color(0xFFFFF8F5),
    unselectedBackgroundColor: Color = YappTheme.colorScheme.staticWhite,
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

            Text(
                text = stringResource(id = iconTextId),
                style = YappTheme.typography.caption2Bold,
                color = contentColor
            )
        }
    }
}