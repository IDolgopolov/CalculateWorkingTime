package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.managers.IdGenerator
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val listDays = MutableLiveData<List<DayInformation>>()
    private val monthAndYearDateValue = MutableLiveData<String>()

    init {
        App.getInstance().mainFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }

    companion object {
        const val INCREASE_MONTH = 1
        const val DECREASE_MONTH = -1
        const val CURRENT_MONTH = 0
    }

    @Inject
    lateinit var calendarInstance: Calendar

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var settingDatabase: SettingRepository

    fun requestDays(delta: Int = CURRENT_MONTH) = viewModelScope.launch {
        calendarInstance.add(Calendar.MONTH, delta)

        val countDays = calendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthAndYearDate = DateParser.getMonthAndYearDate(calendarInstance)

        val days = ArrayList<DayInformation>()

        for (i in 1..countDays) {
            calendarInstance.set(Calendar.DAY_OF_MONTH, i)
            val formattedDate = DateParser.getFormattedDate(calendarInstance)

            var dayInformation = database.getDayInformationBy(formattedDate)

            if (dayInformation == null)
                dayInformation =
                    DayInformation(formattedDate, emptyList(), IdGenerator().getIdByDate())

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

    fun getCountWorkingHoursInDay() =
        settingDatabase.get(SettingRepository.WORKING_HOURS_IN_DAY, getApplication()).toInt()
}