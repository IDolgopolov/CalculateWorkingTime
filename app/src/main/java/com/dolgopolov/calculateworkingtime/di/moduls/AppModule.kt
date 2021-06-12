package com.dolgopolov.calculateworkingtime.di.moduls

import android.app.Application
import android.content.Context
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.repositories.DatabaseImpl
import com.dolgopolov.calculateworkingtime.services.worker_factories.WorkerFactoriesContainer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    private val database = DatabaseImpl(application)

    @Provides
    fun getApplication() = application

    @Provides
    fun getContext(): Context = application

    @Singleton
    @Provides
    fun getWorkerFactory(): WorkerFactoriesContainer = WorkerFactoriesContainer(database)

    @Singleton
    @Provides
    fun getDatabase(): AppDatabase = database
}
