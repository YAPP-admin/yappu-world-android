package com.yapp.feature.history.previous

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HistoryItems(
    modifier: Modifier = Modifier,
    generation: Int,
    position: String,
    slot: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(1f), text = position, style = YappTheme.typography.heading2Bold)
            Box(modifier = Modifier.size(20.dp)) {
                Image(
                    modifier = Modifier.size(16.dp).align(Alignment.Center),
                    painter = painterResource(coreDesignR.drawable.icon_yapp),
                    contentDescription = null
                )
            }

            Text("${generation}기", style = YappTheme.typography.label1NormalMedium)
        }

        slot?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryItems() {
    HistoryItems(
        modifier = Modifier.background(
            color = YappTheme.colorScheme.orange99,
            shape = RoundedCornerShape(12.dp)
        ).padding(16.dp),
        generation = 20,
        position = "운영진"
    )
}
