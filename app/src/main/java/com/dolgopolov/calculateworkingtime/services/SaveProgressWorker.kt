package com.dolgopolov.calculateworkingtime.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.repositories.DatabaseImpl
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SaveProgressWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val database: AppDatabase
) : CoroutineWorker(
    context,
    workerParams
) {
    companion object {
        const val PROJECT_KEY = "PROJECT_KEY"
        const val SECONDS_PASS_KEY = "SECONDS_PASS_KEY"
        const val UNIQUE_NAME = "SAVE_PROGRESS_WORKER"
    }

    private lateinit var project: Project
    private var secondPass = -1L

    override suspend fun doWork(): Result {
        initInputData()
        save()

        return Result.success()
    }

    private fun initInputData() {
        project = Json.decodeFromString(
            inputData.getString(PROJECT_KEY)!!
        )
        secondPass = inputData.getLong(SECONDS_PASS_KEY, -1)
    }

    private suspend fun save() {
        try {
            val workingInfo = WorkingTimeInformation(project, secondPass)
            database.addWorkingInfo(workingInfo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}