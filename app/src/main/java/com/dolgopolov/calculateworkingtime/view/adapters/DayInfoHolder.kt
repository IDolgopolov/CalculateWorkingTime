package com.dolgopolov.calculateworkingtime.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        dayView.findViewById<TextView>(R.id.tv_date).text =
            DateParser.getDateNumberFromFormattedDate(dayInfo.formattedDate)

        val tvWorkingTime = dayView.findViewById<TextView>(R.id.tv_time)
        when {
            dayInfo.listWorkingTime.isEmpty() -> tvWorkingTime.visibility = View.GONE
            else -> tvWorkingTime.text =
                DateParser.getWorkingTimeFormatted(dayInfo.listWorkingTime)
        }

        dayView.findViewById<VerticalProgressBar>(R.id.progress_view).setProgress(
            DateParser.calculateProgress(dayInfo.listWorkingTime, workingHoursInDay)
        )

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