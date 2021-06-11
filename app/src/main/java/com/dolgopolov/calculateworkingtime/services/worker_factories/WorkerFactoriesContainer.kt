package com.dolgopolov.calculateworkingtime.services.worker_factories

import androidx.work.DelegatingWorkerFactory
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerFactoriesContainer @Inject constructor(database: AppDatabase) : DelegatingWorkerFactory() {
    init {
        addFactory(WorkerParamsFactory(database))
    }
}