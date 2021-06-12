package com.dolgopolov.calculateworkingtime.view.base

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.di.components.*
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.services.worker_factories.WorkerFactoriesContainer
import javax.inject.Inject

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var timerFragmentComponent: TimerFragmentComponent
    lateinit var mainFragmentComponent: MainFragmentComponent
    lateinit var projectsFragmentComponent: ProjectsFragmentComponent
    lateinit var settingFragmentComponent: SettingFragmentComponent
    lateinit var oneDayFragmentComponent: OneDayFragmentComponent

    @Inject
    lateinit var workerFactoriesContainer: WorkerFactoriesContainer

    companion object {
        lateinit var app: App
        fun getInstance() = app
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)

        configureWorkManager()
    }


    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactoriesContainer)
            .build()
        WorkManager.initialize(this, config)
    }
}