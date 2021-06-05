package com.dolgopolov.calculateworkingtime.di.moduls

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context, private val application: Application) {
    @Provides
    fun getApplication() = application

    @Provides
    fun getContext() = context
}
