package com.dolgopolov.calculateworkingtime.view

import android.app.Application
import com.dolgopolov.calculateworkingtime.di.components.AppComponent
import com.dolgopolov.calculateworkingtime.di.components.DaggerAppComponent
import com.dolgopolov.calculateworkingtime.di.components.MainFragmentComponent
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule

class App : Application() {
    lateinit var appComponent: AppComponent

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