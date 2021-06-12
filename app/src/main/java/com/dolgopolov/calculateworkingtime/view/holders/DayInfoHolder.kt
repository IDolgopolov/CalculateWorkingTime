package com.dolgopolov.calculateworkingtime.view.holders

import android.view.View
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.view.custom_view.VerticalProgressBar

class DayInfoHolder {
    fun bind(
        dayView: View,
        dayInfo: DayInformation,
        workingHoursInDay: Int,
        onClick: (() -> Unit)? = null
    ): View {
        val tvDate = dayView.findViewById<TextView>(R.id.tv_date)
        val tvWorkingTime = dayView.findViewById<TextView>(R.id.tv_time)
        val progressBar = dayView.findViewById<VerticalProgressBar>(R.id.progress_view)

        tvDate.text = DateParser.getDateNumberFrom(dayInfo.formattedDate)

        when {
            dayInfo.listWorkingTime.isEmpty() -> tvWorkingTime.visibility = View.GONE
            else -> {
                tvWorkingTime.text =
                    DateParser.getWorkingTimeFormatted(dayInfo.listWorkingTime)

                val progress = DateParser.calculateProgressInPercent(
                    dayInfo.listWorkingTime,
                    workingHoursInDay
                )
                progressBar.setProgress(progress)
            }
        }

        markTodayDay(dayInfo, dayView)

        dayView.setOnClickListener {
            onClick?.invoke()
        }

        return dayView
    }

    private fun markTodayDay(dayInfo: DayInformation, dayView: View) {
        if (dayInfo.formattedDate == DateParser.getTodayFormattedDate())
            dayView.setBackgroundResource(R.drawable.background_item_calendar_gradient)
    }
}