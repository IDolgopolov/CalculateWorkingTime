package com.dolgopolov.calculateworkingtime.di.moduls

import android.app.Application
import android.content.Context
import com.dolgopolov.calculateworkingtime.di.components.MainFragmentComponent
import dagger.Module
import dagger.Provides
import java.util.*

@Module(subcomponents = [MainFragmentComponent::class])
class MainFragmentModule {
    @Provides
    fun getCalendar() = Calendar.getInstance()
}