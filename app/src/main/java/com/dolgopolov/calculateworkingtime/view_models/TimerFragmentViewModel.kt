package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.services.TimerWorker
import com.dolgopolov.calculateworkingtime.view.base.App
import javax.inject.Inject

class TimerFragmentViewModel(app: Application) : AndroidViewModel(app) {
    @Inject lateinit var workManager: WorkManager
    @Inject lateinit var fragment: Fragment
    val timePassedFormatted = MutableLiveData<String>()
    val isTimerStarted = MutableLiveData(false)

    init {
        App.getInstance()
            .timerFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }

    fun startTime() {
        val timerWorkRequest = OneTimeWorkRequestBuilder<TimerWorker>().build()

        workManager.enqueueUniqueWork(TimerWorker.UNIQUE_NAME, ExistingWorkPolicy.KEEP, timerWorkRequest)

        workManager.getWorkInfoByIdLiveData(timerWorkRequest.id).observe(fragment) {
            if(it == null) return@observe
            val secondsPassed = it.progress.getInt(TimerWorker.SECONDS_PASSED, -1)
            if(secondsPassed == -1) return@observe

            val formattedTimePassed = DateParser.getFormattedTimePassed(secondsPassed)
            timePassedFormatted.value = formattedTimePassed
        }

        isTimerStarted.value = true
    }

    fun stopTimer() {
        workManager.cancelUniqueWork(TimerWorker.UNIQUE_NAME)
        isTimerStarted.value = false
    }

}