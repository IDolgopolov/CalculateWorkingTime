package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject lateinit var settingRepo: SettingRepository

    init {
        App.getInstance()
            .settingFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }

    fun saveSetting(key: String, value: Any) = viewModelScope.launch {
        settingRepo.save(key, value, getApplication())
    }

    fun getSetting(key: String) : Int {
        return settingRepo.get(key, getApplication()).toInt()
    }
}