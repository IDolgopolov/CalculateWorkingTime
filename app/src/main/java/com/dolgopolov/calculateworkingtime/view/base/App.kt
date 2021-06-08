package com.dolgopolov.calculateworkingtime.view.base

import android.app.Application
import com.dolgopolov.calculateworkingtime.di.components.AppComponent
import com.dolgopolov.calculateworkingtime.di.components.DaggerAppComponent
import com.dolgopolov.calculateworkingtime.di.components.MainFragmentComponent
import com.dolgopolov.calculateworkingtime.di.components.TimerFragmentComponent
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var timerFragmentComponent: TimerFragmentComponent
    lateinit var mainFragmentComponent: MainFragmentComponent

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