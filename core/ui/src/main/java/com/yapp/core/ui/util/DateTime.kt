package com.yapp.core.ui.util

import android.content.Context
import com.yapp.core.ui.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatToDay(context: Context, date: String, showMonth: Boolean = false): String {
    return try {
        val parsedDate = LocalDate.parse(date)
        if (showMonth) {
            context.getString(R.string.time_format_month_day, parsedDate.monthValue, parsedDate.dayOfMonth)
        } else {
            context.getString(R.string.time_format_day, parsedDate.dayOfMonth)
        }
    } catch (e: Exception) {
        date
    }
}

fun formatTimeRange(context: Context, startTime: String?, endTime: String?): String? {
    if (startTime.isNullOrBlank() || endTime.isNullOrBlank()) return null

    return "${formatToKoreanTime(context, startTime)} - ${formatToKoreanTime(context, endTime)}"
}

fun formatToKoreanTime(context: Context, time: String): String {
    return try {
        val parsedTime = LocalTime.parse(time)

        val pattern = context.getString(R.string.time_format_base)
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREAN)
        val base = parsedTime.format(formatter)

        when (parsedTime.minute) {
            0 -> base
            30 -> context.getString(R.string.time_format_half_hour_with_base, base)
            else -> context.getString(R.string.time_format_hour_minute_with_base, base, parsedTime.minute)
        }
    } catch (e: Exception) {
        time
    }
}

fun isPastDate(dateString: String, pattern: String = "yyyy-MM-dd"): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val inputDate = LocalDate.parse(dateString, formatter)
        inputDate.isBefore(LocalDate.now())
    } catch (e: Exception) {
        false
    }
}