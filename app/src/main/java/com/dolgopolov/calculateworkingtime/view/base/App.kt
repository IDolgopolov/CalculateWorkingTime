package com.dolgopolov.calculateworkingtime.view.base

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.di.components.*
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.services.worker_factories.WorkerFactoriesContainer
import com.dolgopolov.calculateworkingtime.view.fragments.MainFragment
import com.dolgopolov.calculateworkingtime.view.fragments.ProjectsFragment
import com.dolgopolov.calculateworkingtime.view.fragments.SettingFragment
import com.dolgopolov.calculateworkingtime.view.fragments.TimerFragment
import javax.inject.Inject

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var timerFragmentComponent: TimerFragmentComponent
    lateinit var mainFragmentComponent: MainFragmentComponent
    lateinit var projectsFragmentComponent: ProjectsFragmentComponent
    lateinit var settingFragmentComponent: SettingFragmentComponent

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