package com.dolgopolov.calculateworkingtime.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.states.TimerState
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TimerWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val SECONDS_PASSED = "TIME_PASSED"
        const val UNIQUE_NAME = "TIMER_WORKER"
        var state = TimerState.Stopped

        private const val INTERVAL = 1000L
    }

    private lateinit var project: Project

    @ObsoleteCoroutinesApi
    override suspend fun doWork(): Result {
        initInputData()

        showForegroundNotification()
        state = TimerState.Playing

        val ticker = ticker(INTERVAL, 0L)
        var secondPassed = 0L
        for (tick in ticker) {
            if (state == TimerState.Stopped) {
                ticker.cancel()
                break
            }

            if (state != TimerState.Paused) {
                secondPassed++
                setProgress(workDataOf(SECONDS_PASSED to secondPassed))
            }
        }

        return Result.success(
            workDataOf(
                SaveProgressWorker.PROJECT_KEY to inputData.getString(SaveProgressWorker.PROJECT_KEY),
                SaveProgressWorker.SECONDS_PASS_KEY to secondPassed
            )
        )
    }


    private suspend fun showForegroundNotification() {
        setForeground(
            ForegroundInfoCreator().createForegroundInfo(
                context,
                id
            )
        )
    }

    private fun initInputData() {
        project = Json.decodeFromString(
            inputData.getString(SaveProgressWorker.PROJECT_KEY)!!
        )
    }
}