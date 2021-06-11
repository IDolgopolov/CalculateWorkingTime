package com.dolgopolov.calculateworkingtime.di.moduls

import android.content.Context
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.repositories.DatabaseImpl
import dagger.Module
import dagger.Provides

@Module
class TimerFragmentViewModelModule {
    @Provides
    fun getWorkManager(context: Context) = WorkManager.getInstance(context)
}