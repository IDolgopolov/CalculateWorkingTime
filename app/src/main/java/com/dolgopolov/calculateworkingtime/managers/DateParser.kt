package com.dolgopolov.calculateworkingtime.managers

import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import java.lang.StringBuilder
import java.util.*

object DateParser {
    private const val DATE_DIVIDER = "."

    fun getFormattedDate(calendar: Calendar) = StringBuilder()
        .append(calendar.get(Calendar.DAY_OF_MONTH))
        .append(DATE_DIVIDER)
        .append(calendar.get(Calendar.MONTH) + 1)
        .append(DATE_DIVIDER)
        .append(calendar.get(Calendar.YEAR))
        .toString()

    fun getDateNumberFromFormattedDate(date: String) = date.substringBefore(DATE_DIVIDER)

    fun getWorkingTimeFormatted(timeMillis: Long): String {
        val hours = timeMillis / (1000 * 60 * 60)
        val minutes = (timeMillis - hours * 60 * 60 * 1000) / (1000 * 60)
        return StringBuilder()
            .append(hours)
            .append("h")
            .append("\n")
            .append(minutes)
            .append("m")
            .toString()
    }

    fun getWorkingTimeFormatted(list: List<WorkingTimeInformation>) = getWorkingTimeFormatted(list.sumOf { it.time })
}