package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.model.ScheduleProgressPhase

@Composable
fun SessionChip(
    modifier: Modifier = Modifier,
    progressPhase: ScheduleProgressPhase
) {
    val colorType = when(progressPhase) {
        ScheduleProgressPhase.TODAY -> {
            ChipColorType.Main
        }
        ScheduleProgressPhase.DONE -> {
            ChipColorType.Gray
        }
        ScheduleProgressPhase.ONGOING -> {
            ChipColorType.Sub
        }
        else -> return
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
            SessionChip(progressPhase = ScheduleProgressPhase.TODAY)
            SessionChip(progressPhase = ScheduleProgressPhase.DONE)
            SessionChip(progressPhase = ScheduleProgressPhase.ONGOING)
        }
    }
}

