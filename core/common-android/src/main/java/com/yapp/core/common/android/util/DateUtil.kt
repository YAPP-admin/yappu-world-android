package com.yapp.core.common.android.util

import java.time.LocalDate

fun LocalDate.toMonthDateRange(): Pair<String, String> {
    val startOfMonth = this.withDayOfMonth(1)
    val endOfMonth = this.withDayOfMonth(this.lengthOfMonth())

    return Pair(startOfMonth.toString(), endOfMonth.toString())
}