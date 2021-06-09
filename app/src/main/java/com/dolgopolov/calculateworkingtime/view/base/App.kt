package com.dolgopolov.calculateworkingtime.view.base

import android.app.Application
import com.dolgopolov.calculateworkingtime.di.components.*
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var timerFragmentComponent: TimerFragmentComponent
    lateinit var mainFragmentComponent: MainFragmentComponent
    lateinit var projectsFragmentComponent: ProjectsFragmentComponent

    companion object {
        lateinit var app: App
        fun getInstance() = app
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, this))
            .build()
    }

}