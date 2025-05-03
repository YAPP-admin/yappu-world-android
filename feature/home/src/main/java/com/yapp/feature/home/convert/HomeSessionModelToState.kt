package com.yapp.feature.home.convert

import com.yapp.feature.home.HomeState
import com.yapp.model.DateGroupedSchedule

fun List<DateGroupedSchedule>.toState() = flatMap { session ->
    session.schedules.map { schedule ->
        HomeState.Session(
            id = schedule.id,
            title = schedule.name,
            date = schedule.date,
            place = schedule.place.orEmpty(),
            startTime = schedule.time.orEmpty(),
            endTime = schedule.endTime.orEmpty(),
            startDayOfWeek = session.dayOfTheWeek,
            progressPhase = schedule.scheduleProgressPhase
        )
    }
}
