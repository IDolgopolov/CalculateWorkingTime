package com.dolgopolov.calculateworkingtime.managers

import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DateParser {
    private const val DATE_DIVIDER = "."
    private const val HOURS_IN_DAY = 24L
    private const val SECONDS_IN_MINUTE = 60L
    private const val SECONDS_IN_HOUR = 60 * 60L
    private const val SECONDS_IN_DAY = 24 * 60 * 60L


    fun getFormattedDate(calendar: Calendar = Calendar.getInstance()): String {
        val datePattern = "dd${DATE_DIVIDER}MM${DATE_DIVIDER}yyyy"
        val format = SimpleDateFormat(datePattern, Locale.getDefault())
        return format.format(calendar.time)
    }

    fun getDateNumberFrom(formattedDate: String) = formattedDate.substringBefore(DATE_DIVIDER)

    fun getWorkingTimeFormatted(timeSeconds: Long, displaySeconds: Boolean = false): String {
        val hours = timeSeconds / SECONDS_IN_HOUR
        val minutes = (timeSeconds - hours * SECONDS_IN_HOUR) / SECONDS_IN_MINUTE
        val seconds = (timeSeconds - hours * SECONDS_IN_HOUR - minutes * SECONDS_IN_MINUTE)

        val formatted = StringBuilder()
            .append(hours)
            .append("h")
            .append("\n")
            .append(minutes)
            .append("m")

        if (displaySeconds) {
            formatted.append(" ")
                .append(seconds)
                .append("s")
        }

        return formatted.toString()
    }

    fun getWorkingTimeFormatted(list: List<WorkingTimeInformation>): String {
        val secondsSum = list.sumOf { it.seconds }
        return getWorkingTimeFormatted(secondsSum)
    }

    fun getMonthAndYearDate(calendar: Calendar): String {
        val datePattern = "LLLL"
        val monthName = SimpleDateFormat(datePattern, Locale.getDefault()).format(calendar.time)
        return StringBuilder()
            .append(monthName)
            .append("\n")
            .append(calendar.get(Calendar.YEAR))
            .toString()
    }

    fun getTodayFormattedDate() = getFormattedDate()

    fun getFormattedTimePassed(secondsPassed: Long): String {
        val datePattern = "HH:mm:ss"
        val format = SimpleDateFormat(datePattern, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT")
        val dateObj = Date(secondsPassed.toMillis())
        return format.format(dateObj)
    }

    //need if the timer was started on one day and stopped on another
    fun splitWorkingTime(
        info: WorkingTimeInformation
    ): List<DayInformation> {
        val countDays = getCountDays(info)
        val idGenerator = IdGenerator()
        val now = Calendar.getInstance()

        //IF USER WORK ONLY IN TODAY DAY
        if (countDays == 1L) {
            return listOf(
                DayInformation(
                    getTodayFormattedDate(),
                    listOf(info),
                    idGenerator.getIdByDate(now)
                )
            )
        }

        //IF USER WORK SEVERAL DAYS IN ROW
        val listDays = ArrayList<DayInformation>()

        //HOW MANY WORK TODAY
        val secondsPassToday = (now.get(Calendar.SECOND) +
                now.get(Calendar.MINUTE) * SECONDS_IN_MINUTE +
                now.get(Calendar.HOUR) * SECONDS_IN_HOUR)
        val workingTimeInfoToday =
            WorkingTimeInformation(info.project, secondsPassToday)
        listDays.add(
            DayInformation(
                getTodayFormattedDate(),
                listOf(workingTimeInfoToday),
                idGenerator.getIdByDate(now)
            )
        )

        //HOW MANY FULL DAYS PASSED
        //MINUS FIRST AND LAST PARTS DAY
        val countFullDays = countDays - 2

        if (countFullDays > 0) {
            for (i in 0 until countFullDays) {
                val prevDayWorkingTime =
                    WorkingTimeInformation(
                        workingTimeInfoToday.project,
                        SECONDS_IN_DAY
                    )
                now.add(Calendar.DAY_OF_MONTH, -1)

                val prevDayInfo = DayInformation(
                    getFormattedDate(now),
                    listOf(prevDayWorkingTime),
                    idGenerator.getIdByDate(now)
                )
                listDays.add(prevDayInfo)
            }
        }

        //DAY, WHEN USER STARTED WORK
        val lastPrevDayWorkingInfo = WorkingTimeInformation(
            workingTimeInfoToday.project,
            workingTimeInfoToday.seconds - secondsPassToday - countFullDays * SECONDS_IN_DAY
        )
        now.add(Calendar.DAY_OF_MONTH, -1)
        val lastPrevDayInfo = DayInformation(
            getFormattedDate(now),
            listOf(lastPrevDayWorkingInfo),
            idGenerator.getIdByDate(now)
        )
        listDays.add(lastPrevDayInfo)

        return listDays
    }

    fun calculateProgressInPercent(
        list: List<WorkingTimeInformation>,
        workingHoursInDay: Int
    ): Float {
        return (list.sumOf { it.seconds }.toFloat() / (workingHoursInDay * SECONDS_IN_HOUR)) * 100
    }

    fun convertMinutesToSeconds(minutes: Long) = minutes * SECONDS_IN_MINUTE

    private fun getCountDays(info: WorkingTimeInformation): Long {
        var countDays = info.seconds / SECONDS_IN_DAY
        if (info.seconds % SECONDS_IN_DAY != 0L) countDays++
        return countDays
    }

    private fun Long.toMillis() = this * 1000L
}