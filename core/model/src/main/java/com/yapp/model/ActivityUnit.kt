package com.yapp.model

import androidx.compose.runtime.Stable

@Stable
data class ActivityUnit(
    val generation: Int? = null,
    val position: String? = null,
) {
    operator fun plus(previousActivityUnit: List<ActivityUnit>): List<ActivityUnit> {
        return previousActivityUnit.toMutableList().apply { add(0, this@ActivityUnit) }
    }
}