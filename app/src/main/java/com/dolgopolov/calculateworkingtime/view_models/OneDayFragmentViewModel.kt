package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneDayFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val error = MutableLiveData<String?>()

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

    fun update(info: WorkingTimeInformation, dayId: Int, newTimeMin: Long?) =
        viewModelScope.launch {
            if (newTimeMin == null) {
                return@launch
            }

            info.seconds = DateParser.convertMinutesToSeconds(newTimeMin)
            database.updateWorkingInfo(info, dayId)
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