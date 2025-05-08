package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.model.SessionProgressPhase

@Composable
fun SessionChip(
    modifier: Modifier = Modifier,
    progressPhase: SessionProgressPhase,
) {
    val colorType = when (progressPhase) {
        SessionProgressPhase.TODAY -> {
            ChipColorType.Main
        }
        SessionProgressPhase.DONE, SessionProgressPhase.PENDING -> {
            ChipColorType.Gray
        }
        SessionProgressPhase.UPCOMING -> {
            ChipColorType.Sub
        }
    }

    YappChipSmall(
        modifier = modifier,
        text = progressPhase.title,
        colorType = colorType,
        isFill = true
    )
}

@Preview(showBackground = true)
@Composable
private fun SessionChipPreview() {
    YappBackground {
        Row {
            SessionChip(progressPhase = SessionProgressPhase.TODAY)
            SessionChip(progressPhase = SessionProgressPhase.DONE)
            SessionChip(progressPhase = SessionProgressPhase.PENDING)
            SessionChip(progressPhase = SessionProgressPhase.UPCOMING)
        }
    }
}

