package com.dolgopolov.calculateworkingtime.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneDayFragmentViewModel : ViewModel() {
    init {
        App.getInstance()
            .oneDayFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var database: AppDatabase

    val dayInformation = MutableLiveData<DayInformation>()

    fun update(info: DayInformation) = viewModelScope.launch {
        database.updateDayInfo(info)
        dayInformation.value = info
    }

    fun updateDayOffStatus(info: DayInformation, isDayOff: Boolean) {
        if (info.isDayOff != isDayOff) {
            info.isDayOff = isDayOff
            update(info)
        }
    }

    fun setDayInfo(info: DayInformation) {
        dayInformation.value = info
    }
}