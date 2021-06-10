package com.dolgopolov.calculateworkingtime.di.moduls

import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import dagger.Module

@Module
class SettingFragmentViewModelModule {
    fun getSettingRepository() = SettingRepository()
}