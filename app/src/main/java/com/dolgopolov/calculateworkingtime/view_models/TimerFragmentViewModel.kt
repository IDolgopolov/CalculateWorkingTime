package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.services.TimerWorker
import com.dolgopolov.calculateworkingtime.states.TimerState
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.fragments.ProjectsFragment
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TimerFragmentViewModel(app: Application) : AndroidViewModel(app) {
    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var fragment: Fragment

    val timePassedFormatted = MutableLiveData<String>()
    val isTimerStarted = MutableLiveData(TimerState.Stopped)
    val selectedProject = MutableLiveData<Project>()

    init {
        App.getInstance()
            .timerFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)

        viewModelScope.launch {
            subscribe()
        }
    }

    fun playPauseTimer() = viewModelScope.launch {
        when (isTimerStarted.value!!) {
            TimerState.Playing -> pause()
            TimerState.Stopped -> start()
            TimerState.Paused -> play()
        }
    }

    fun stopTimer() = viewModelScope.launch {
        workManager.cancelUniqueWork(TimerWorker.UNIQUE_NAME)
    }

    fun decodeSelectedProject(bundle: Bundle) {
        val encodedProject = bundle.getString(ProjectsFragment.PROJECT_KEY)
        if(encodedProject != null) {
            val project = Json.decodeFromString<Project>(encodedProject)
            selectedProject.value = project
        }
    }

    private fun subscribe() {
        workManager
            .getWorkInfosForUniqueWorkLiveData(TimerWorker.UNIQUE_NAME)
            .observe(fragment) { listInfo ->
                isTimerStarted.value = getTimerState(listInfo)

                listInfo.forEach { info ->
                    if (info == null) return@observe
                    val secondsPassed = info.progress.getInt(TimerWorker.SECONDS_PASSED, -1)
                    if (secondsPassed == -1) return@observe

                    val formattedTimePassed = DateParser.getFormattedTimePassed(secondsPassed)
                    timePassedFormatted.value = formattedTimePassed
                }
            }
    }

    private fun start() {
        val timerWorkRequest = OneTimeWorkRequestBuilder<TimerWorker>().build()
        workManager.enqueueUniqueWork(
            TimerWorker.UNIQUE_NAME,
            ExistingWorkPolicy.KEEP,
            timerWorkRequest
        )
    }

    private fun play() {
        TimerWorker.isPaused = false
    }

    private fun pause() {
        TimerWorker.isPaused = true
        isTimerStarted.value = TimerState.Paused
    }

    private fun getTimerState(listWorkInfo: List<WorkInfo>): TimerState {
        listWorkInfo.forEach {
            if (it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING) {
                return if(TimerWorker.isPaused)
                    TimerState.Paused
                else
                    TimerState.Playing
            }
        }
        return TimerState.Stopped
    }
}