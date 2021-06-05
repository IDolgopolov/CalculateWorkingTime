package com.dolgopolov.calculateworkingtime.view.custom_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import java.util.*

class CalendarView(private val context: Context) {
    private var view: View? = null
    private var containerDays: ViewGroup? = null
    private var tvMonthAndYearDate: TextView? = null

    var onPreviousMonthClick: (() -> Unit)? = null
    var onNextMonthClick: (() -> Unit)? = null

    fun init(container: ViewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.calendar_view, container, false)
        containerDays = view?.findViewById(R.id.container_days)
        tvMonthAndYearDate = view?.findViewById(R.id.tv_month_and_year)
        val bPrevMonth = view?.findViewById<View>(R.id.b_prev)
        val bNextMonth = view?.findViewById<View>(R.id.b_next)

        bPrevMonth?.setOnClickListener {
            onPreviousMonthClick?.invoke()
        }

        bNextMonth?.setOnClickListener {
            onNextMonthClick?.invoke()
        }

        container.addView(view)
    }

    fun setMonthAndYearDate(monthName: String) {
        tvMonthAndYearDate?.text = monthName
    }

    fun setDays(days: List<DayInformation>) {
        containerDays?.removeAllViews()

        days.forEach { dayInfo ->
            val dayView =
                LayoutInflater.from(context).inflate(R.layout.item_calendar, containerDays, false)
            dayView.findViewById<TextView>(R.id.tv_date).text =
                DateParser.getDateNumberFromFormattedDate(dayInfo.formattedDate)
            dayView.findViewById<TextView>(R.id.tv_time).text =
                DateParser.getWorkingTimeFormatted(dayInfo.listWorkingTime)

            markTodayDay(dayInfo, dayView)

            containerDays?.addView(dayView)
        }
    }

    fun markTodayDay(dayInfo: DayInformation, dayView: View) {
        if(dayInfo.formattedDate == DateParser.getTodayFormattedDate())
            dayView.setBackgroundResource(R.drawable.background_item_calendar_gradient)
    }

    fun onDestroyView() {
        view = null
        containerDays = null
        tvMonthAndYearDate = null
    }
}