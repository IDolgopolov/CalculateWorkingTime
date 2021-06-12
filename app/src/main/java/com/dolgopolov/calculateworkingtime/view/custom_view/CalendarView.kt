package com.dolgopolov.calculateworkingtime.view.custom_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.view.holders.DayInfoHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalendarView @Inject constructor(private val context: Context) {
    private var view: View? = null
    private var containerDays: ViewGroup? = null
    private var tvMonthAndYearDate: TextView? = null

    var onPreviousMonthClick: (() -> Unit)? = null
    var onNextMonthClick: (() -> Unit)? = null
    var onDaySelected: ((DayInformation) -> Unit)? = null

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

    suspend fun setDays(days: List<DayInformation>, workingHoursInDay: Int) =
        withContext(Dispatchers.Main) {
            containerDays?.removeAllViews()

            val views = ArrayList<View>()
            withContext(Dispatchers.Default) {
                val dayViewHolder = DayInfoHolder()

                days.forEach { dayInfo ->
                    val dayView = createDayView()

                    dayViewHolder.bind(
                        dayView,
                        dayInfo,
                        workingHoursInDay,
                        onClick = {
                            onDaySelected?.invoke(dayInfo)
                        }
                    )
                    views.add(dayView)
                }
            }

            views.forEach { containerDays?.addView(it) }
        }

    private fun createDayView() = LayoutInflater.from(context)
        .inflate(
            R.layout.item_calendar,
            containerDays,
            false
        )

    fun onDestroyView() {
        view = null
        containerDays = null
        tvMonthAndYearDate = null
    }
}