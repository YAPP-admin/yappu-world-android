package com.yapp.core.ui.util

import android.content.Context
import com.yapp.core.ui.R
import java.time.LocalDate
import java.time.LocalDateTime
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

fun formatScheduleTimeRange(
    context: Context,
    date: String?,
    startDayOfWeek: String?,
    time: String?,
    endDate: String?,
    endDayOfWeek: String?,
    endTime: String?,
): String? {
    val startDate = parseDate(date) ?: return null
    val startDOW  = startDayOfWeek?.takeIf { it.isNotBlank() } ?: return null
    val startTime = parseTime(time) ?: return null
    val endTimeParsed = parseTime(endTime) ?: return null

    val endDateParsed = parseDate(endDate)
    val dateFmt = DateTimeFormatter.ofPattern("MM.dd")
    val timeFmt = DateTimeFormatter.ofPattern("HH:mm")

    return if (endDateParsed != null && endDateParsed != startDate) {
        val endDOW = endDayOfWeek?.takeIf { it.isNotBlank() } ?: return null
        context.getString(
            R.string.schedule_range_cross_day,
            startDate.format(dateFmt),
            startDOW,
            startTime.format(timeFmt),
            endDateParsed.format(dateFmt),
            endDOW,
            endTimeParsed.format(timeFmt)
        )
    } else {
        context.getString(
            R.string.schedule_range_same_day,
            startDate.format(dateFmt),
            startDOW,
            startTime.format(timeFmt),
            endTimeParsed.format(timeFmt)
        )
    }
}

private fun parseDate(s: String?): LocalDate? =
    s?.takeIf { it.isNotBlank() }?.let {
        runCatching { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }.getOrNull()
    }

private fun parseTime(s: String?): LocalTime? =
    s?.takeIf { it.isNotBlank() }?.let {
        runCatching { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }.getOrNull()
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

fun formatDateTime(context: Context, input: String): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME

    val dateTime = try {
        LocalDateTime.parse(input, inputFormatter)
    } catch (e: Exception) {
        return input
    }

    val outputPattern = context.getString(R.string.date_time_format)
    val outputFormatter = DateTimeFormatter.ofPattern(outputPattern, Locale.KOREAN)

    return dateTime.format(outputFormatter)
}

fun formatSessionDateTime(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime
): Pair<String, String> {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd")
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN)
    val timeFormatter = DateTimeFormatter.ofPattern("a h시", Locale.KOREAN)
    val timeWithMinuteFormatter = DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREAN)

    val formattedStartDate = startDateTime.format(dateFormatter)
    val formattedEndDate = endDateTime.format(dateFormatter)
    val startDayOfWeek = startDateTime.format(dayOfWeekFormatter)
    val endDayOfWeek = endDateTime.format(dayOfWeekFormatter)

    val formattedStartTime = if (startDateTime.minute == 0) {
        startDateTime.format(timeFormatter)
    } else {
        startDateTime.format(timeWithMinuteFormatter)
    }

    val formattedEndTime = if (endDateTime.minute == 0) {
        endDateTime.format(timeFormatter)
    } else {
        endDateTime.format(timeWithMinuteFormatter)
    }

    val timeRange = "$formattedStartTime ~ $formattedEndTime"

    return if (formattedStartDate == formattedEndDate) {
        Pair(
            "$formattedStartDate ($startDayOfWeek)",
            timeRange
        )
    } else {
        Pair(
            "$formattedStartDate ($startDayOfWeek) $formattedStartTime",
            "~ $formattedEndDate ($endDayOfWeek) $formattedEndTime"
        )
    }
}