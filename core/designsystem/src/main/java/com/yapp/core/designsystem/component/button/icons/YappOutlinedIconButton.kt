package com.yapp.core.designsystem.component.button.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YappOutlinedIconButtonBasic(
    modifier: Modifier = Modifier,
    size: Dp,
    iconSize: Dp,
    borderColor: Color,
    iconTint: Color = Color.Unspecified,
    @DrawableRes resourceId: Int,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape,
            )
            .clip(CircleShape)
            .yappClickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = modifier
                .size(iconSize),
            painter = painterResource(resourceId),
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun YappOutlinedIconButtonSmall(
    modifier: Modifier = Modifier,
    size: Dp = OutlinedIconButtonDefaults.sizeSmall,
    iconSize: Dp = OutlinedIconButtonDefaults.iconSizeSmall,
    borderColor: Color = OutlinedIconButtonDefaults.borderColor,
    iconTint: Color = Color.Unspecified,
    @DrawableRes resourceId: Int,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    YappOutlinedIconButtonBasic(
        modifier = modifier,
        size = size,
        iconSize = iconSize,
        borderColor = borderColor,
        iconTint = iconTint,
        resourceId = resourceId,
        contentDescription = contentDescription,
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedIconButtonSmallPreview() {
    YappTheme {
        YappOutlinedIconButtonSmall(
            resourceId = R.drawable.icon_check,
            contentDescription = "Add",
            onClick = {}
        )
    }
}
