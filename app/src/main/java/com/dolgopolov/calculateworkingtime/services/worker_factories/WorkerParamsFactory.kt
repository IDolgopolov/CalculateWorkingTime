package com.dolgopolov.calculateworkingtime.services.worker_factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.services.SaveProgressWorker
import com.dolgopolov.calculateworkingtime.services.TimerWorker

class WorkerParamsFactory(private val database: AppDatabase) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            SaveProgressWorker::class.java.name -> {
                SaveProgressWorker(appContext, workerParameters, database)
            }
            TimerWorker::class.java.name -> {
                TimerWorker(appContext, workerParameters)
            }
            else -> null
        }
    }
}