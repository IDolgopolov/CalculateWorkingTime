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

    fun requestDays() = viewModelScope.launch {
        val instance = Calendar.getInstance()
        val countDays = instance.getActualMaximum(Calendar.DAY_OF_MONTH)

        val days = ArrayList<DayInformation>()
        for (i in 1..countDays) {
            instance.set(Calendar.DAY_OF_MONTH, i)
            val formattedDate = DateParser.getFormattedDate(instance)
            var dayInformation =
                DatabaseController().getDayInformationBy(formattedDate, getContext())

            if (dayInformation == null) {
                dayInformation = DayInformation(
                    formattedDate,
                    emptyList()
                )
            }
            days.add(dayInformation)
        }
        listDays.value = days
    }

    fun getDays(): LiveData<List<DayInformation>> {
        if (listDays.value == null)
            requestDays()
        return listDays
    }

    private fun getContext() = getApplication<Application>().applicationContext
}