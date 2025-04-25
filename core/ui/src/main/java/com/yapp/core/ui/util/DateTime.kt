package com.yapp.core.ui.util

import android.content.Context
import com.yapp.core.ui.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun formatToDay(context: Context, date: String): String {
    return try {
        val parsedDate = LocalDate.parse(date)
        context.getString(R.string.time_format_day, parsedDate.dayOfMonth)
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
        val hour = parsedTime.hour
        val minute = parsedTime.minute

        val isAm = hour < 12
        val hour12 = if (hour == 0 || hour == 12) 12 else hour % 12

        when (minute) {
            0 -> context.getString(
                if (isAm) R.string.time_format_hour else R.string.time_format_hour_pm,
                hour12
            )
            30 -> context.getString(
                if (isAm) R.string.time_format_half_hour else R.string.time_format_half_hour_pm,
                hour12
            )
            else -> context.getString(
                if (isAm) R.string.time_format_hour_minute else R.string.time_format_hour_minute_pm,
                hour12, minute
            )
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