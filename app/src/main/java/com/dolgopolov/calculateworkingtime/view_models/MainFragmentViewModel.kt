package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.*
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.repositories.DatabaseController
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainFragmentViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val listDays = MutableLiveData<List<DayInformation>>()
    private val monthAndYearDateValue = MutableLiveData<String>()

    companion object {
        const val INCREASE_MONTH = 1
        const val DECREASE_MONTH = -1
        const val CURRENT_MONTH = 0
    }

    @Inject lateinit var calendarInstance: Calendar

    fun requestDays(delta: Int = CURRENT_MONTH) = viewModelScope.launch {
        calendarInstance.add(Calendar.MONTH, delta)

        val countDays = calendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthAndYearDate = DateParser.getMonthAndYearDate(calendarInstance)

        val days = ArrayList<DayInformation>()
        val dbController = DatabaseController()

        for (i in 1..countDays) {
            calendarInstance.set(Calendar.DAY_OF_MONTH, i)
            val formattedDate = DateParser.getFormattedDate(calendarInstance)

            var dayInformation = dbController.getDayInformationBy(formattedDate, getContext())

            if (dayInformation == null)
                dayInformation = DayInformation(formattedDate)

            days.add(dayInformation)
        }
        listDays.value = days
        monthAndYearDateValue.value = monthAndYearDate
    }

    fun getDays(): LiveData<List<DayInformation>> {
        if (listDays.value == null)
            requestDays()
        return listDays
    }

    fun getMonthName() = monthAndYearDateValue

    private fun getContext() = getApplication<Application>().applicationContext
}