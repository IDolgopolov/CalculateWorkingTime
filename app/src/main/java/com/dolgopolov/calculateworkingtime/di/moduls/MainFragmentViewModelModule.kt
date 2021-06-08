package com.dolgopolov.calculateworkingtime.di.moduls

import dagger.Module
import dagger.Provides
import java.util.*

@Module
class MainFragmentViewModelModule {
    @Provides
    fun getCalendar(): Calendar = Calendar.getInstance()
}