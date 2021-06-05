package com.dolgopolov.calculateworkingtime.view.custom_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import java.util.*

class CalendarView(private val context: Context) {
    private lateinit var view: View
    private lateinit var containerDays: ViewGroup

    fun init(container: ViewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.calendar_view, container, false)
        containerDays = view.findViewById(R.id.container_days)

        container.addView(view)
    }

    fun setDays(days: List<DayInformation>) {
        containerDays.removeAllViews()
        days.forEach {
            val dayView = LayoutInflater.from(context).inflate(R.layout.item_calendar, containerDays, false)
            dayView.findViewById<TextView>(R.id.tv_date).text = DateParser.getDateNumberFromFormattedDate(it.formattedDate)
            dayView.findViewById<TextView>(R.id.tv_time).text = DateParser.getWorkingTimeFormatted(it.listWorkingTime)

            containerDays.addView(dayView)
        }
    }
}