package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.model.SessionProgressPhase

@Composable
fun SessionChip(
    modifier: Modifier = Modifier,
    progressPhase: SessionProgressPhase,
) {
    val (colorType, isFill) = when (progressPhase) {
        SessionProgressPhase.TODAY, SessionProgressPhase.ONGOING -> {
            ChipColorType.Main to true
        }
        SessionProgressPhase.DONE -> {
            ChipColorType.Gray to true
        }
        SessionProgressPhase.PENDING -> {
            ChipColorType.Sub to false
        }
        SessionProgressPhase.NONE -> {
            ChipColorType.Yellow to true
        }
    }

    YappChipSmall(
        modifier = modifier,
        text = progressPhase.title,
        colorType = colorType,
        isFill = isFill
    )
}

@Preview(showBackground = true)
@Composable
private fun SessionChipPreview() {
    YappTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SessionChip(progressPhase = SessionProgressPhase.TODAY)
            SessionChip(progressPhase = SessionProgressPhase.DONE)
            SessionChip(progressPhase = SessionProgressPhase.PENDING)
            SessionChip(progressPhase = SessionProgressPhase.ONGOING)
            SessionChip(progressPhase = SessionProgressPhase.NONE)
        }
    }
}

