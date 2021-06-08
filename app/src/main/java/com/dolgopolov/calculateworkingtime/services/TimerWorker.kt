package com.dolgopolov.calculateworkingtime.services

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.dolgopolov.calculateworkingtime.R
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import javax.inject.Inject
import kotlin.concurrent.timer

class TimerWorker @Inject constructor(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    companion object {
        const val SECONDS_PASSED = "TIME_PASSED"
        const val UNIQUE_NAME = "TIMER_WORKER"
        var isPaused = false
    }

    @ObsoleteCoroutinesApi
    override suspend fun doWork(): Result {
        isPaused = false
        setForeground(
            ForegroundInfoCreator().createForegroundInfo(
                context,
                id
            )
        )

        val ticker = ticker(1000L, 0L)
        var secondPassed = 0
        for (tick in ticker) {
            if (isStopped) break

            if (!isPaused)
                secondPassed++
            setProgress(workDataOf(SECONDS_PASSED to secondPassed))
        }
        ticker.cancel()
        return Result.success()
    }
}