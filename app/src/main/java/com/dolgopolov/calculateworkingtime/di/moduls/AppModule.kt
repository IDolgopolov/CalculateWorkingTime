package com.dolgopolov.calculateworkingtime.di.moduls

import android.app.Application
import android.content.Context
import com.dolgopolov.calculateworkingtime.di.components.MainFragmentComponent
import com.dolgopolov.calculateworkingtime.di.components.TimerFragmentComponent
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.repositories.DatabaseImpl
import com.dolgopolov.calculateworkingtime.services.worker_factories.WorkerFactoriesContainer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    fun getApplication() = application

    @Provides
    fun getContext() : Context = application

    @Provides
    fun getDatabase() : AppDatabase = DatabaseImpl(application)

    @Provides
    fun getWorkerFactory() : WorkerFactoriesContainer = WorkerFactoriesContainer(getDatabase())
}
