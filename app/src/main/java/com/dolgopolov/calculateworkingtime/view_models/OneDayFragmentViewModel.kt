package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneDayFragmentViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance()
            .oneDayFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var settingDatabase: SettingRepository

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

    fun getCountWorkingHoursInDay() =
        settingDatabase.get(SettingRepository.WORKING_HOURS_IN_DAY, getApplication()).toInt()
}