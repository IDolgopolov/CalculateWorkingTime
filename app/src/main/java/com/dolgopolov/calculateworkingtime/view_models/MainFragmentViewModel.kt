package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.*
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.repositories.DatabaseController
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val listDays = MutableLiveData<List<DayInformation>>()
    private val monthAndYearDateValue = MutableLiveData<String>()
    private val deltaCurrentMonth = MutableLiveData(0)

    companion object {
        private const val MONTHS_IN_YEAR = 12
    }


    fun requestDays() = viewModelScope.launch {
        val instance = Calendar.getInstance()

        instance.add(Calendar.MONTH, deltaCurrentMonth.value!!)

        val countDays = instance.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthAndYearDate = DateParser.getMonthAndYearDate(instance)

        val days = ArrayList<DayInformation>()
        val dbController = DatabaseController()

        for (i in 1..countDays) {
            instance.set(Calendar.DAY_OF_MONTH, i)
            val formattedDate = DateParser.getFormattedDate(instance)

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

    fun increaseCurrentMonth() {
        deltaCurrentMonth.value = deltaCurrentMonth.value!! + 1
    }

    fun decreaseCurrentMonth() {
        deltaCurrentMonth.value = deltaCurrentMonth.value!! - 1
    }


    private fun getContext() = getApplication<Application>().applicationContext
}