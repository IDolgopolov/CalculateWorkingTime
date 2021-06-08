package com.dolgopolov.calculateworkingtime.managers

import android.annotation.SuppressLint
import android.text.format.DateFormat
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.StringBuilder

object DateParser {
    private const val DATE_DIVIDER = "."

    fun getFormattedDate(calendar: Calendar = Calendar.getInstance()) = StringBuilder()
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

    fun getWorkingTimeFormatted(list: List<WorkingTimeInformation>) =
        getWorkingTimeFormatted(list.sumOf { it.time })

    fun getMonthAndYearDate(calendar: Calendar): String {
        val monthName = SimpleDateFormat("LLLL", Locale.getDefault()).format(calendar.time)
        return StringBuilder()
            .append(monthName)
            .append("\n")
            .append(calendar.get(Calendar.YEAR))
            .toString()
    }

    fun getTodayFormattedDate() = getFormattedDate()

    @SuppressLint("SimpleDateFormat")
    fun getFormattedTimePassed(secondsPassed: Int): String {
        return DateFormat.format("HH:mm:ss", secondsPassed * 1000L).toString()
    }
}