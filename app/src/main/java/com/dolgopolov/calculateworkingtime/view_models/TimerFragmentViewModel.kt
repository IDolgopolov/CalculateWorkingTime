package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.services.SaveProgressWorker
import com.dolgopolov.calculateworkingtime.services.TimerWorker
import com.dolgopolov.calculateworkingtime.states.TimerState
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.fragments.ProjectsFragment
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.StringBuilder
import javax.inject.Inject

class TimerFragmentViewModel(app: Application) : AndroidViewModel(app) {
    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var fragment: Fragment

    val timePassedFormatted = MutableLiveData<String>()
    val isTimerStarted = MutableLiveData(TimerWorker.state)
    val selectedProject = MutableLiveData<Project>()
    val error = MutableLiveData<String>()
    val todayDate = MutableLiveData<String>()

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
        if (selectedProject.value == null) {
            error.postValue("Необходимо выбрать проект")
            return@launch
        }

        when (TimerWorker.state) {
            TimerState.Playing -> pause()
            TimerState.Stopped -> {
                start()
                play()
            }
            TimerState.Paused -> play()
        }
    }

    fun stopTimer() = viewModelScope.launch {
        TimerWorker.state = TimerState.Stopped
        isTimerStarted.value = TimerState.Stopped
    }

    fun decodeSelectedProject(bundle: Bundle) {
        val encodedProject = bundle.getString(ProjectsFragment.PROJECT_KEY)
        if (encodedProject != null) {
            val project = Json.decodeFromString<Project>(encodedProject)
            selectedProject.value = project
        }
    }

    fun getTodayDate() : LiveData<String> {
        if(todayDate.value == null) {
            val date = DateParser.getTodayFormattedDate()
            todayDate.value = date
        }
        return todayDate
    }

    private fun subscribe() {
        workManager
            .getWorkInfosForUniqueWorkLiveData(TimerWorker.UNIQUE_NAME)
            .observe(fragment) { listInfo ->
                viewModelScope.launch {
                    listInfo.forEach { info ->
                        val secondsPassed = info.progress.getLong(TimerWorker.SECONDS_PASSED, -1L)
                        if (secondsPassed == -1L) return@launch

                        val formattedTimePassed = DateParser.getFormattedTimePassed(secondsPassed)
                        timePassedFormatted.value = formattedTimePassed
                    }
                }
            }
    }

    private fun start() {
        val project = selectedProject.value!!
        val projectEncoded = Json.encodeToString(project)
        val data = Data.Builder()
        data.putString(SaveProgressWorker.PROJECT_KEY, projectEncoded)

        val timerWorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setInputData(data.build())
            .build()
        val saveProgressRequest = OneTimeWorkRequestBuilder<SaveProgressWorker>()
            .build()

        workManager.beginUniqueWork(
            TimerWorker.UNIQUE_NAME,
            ExistingWorkPolicy.KEEP,
            timerWorkRequest
        )
            .then(saveProgressRequest)
            .enqueue()

    }

    private fun play() {
        TimerWorker.state = TimerState.Playing
        isTimerStarted.value = TimerState.Playing
    }

    private fun pause() {
        TimerWorker.state = TimerState.Paused
        isTimerStarted.value = TimerState.Paused
    }
}