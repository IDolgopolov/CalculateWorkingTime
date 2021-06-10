package com.dolgopolov.calculateworkingtime.view.base

import android.app.Application
import androidx.fragment.app.Fragment
import com.dolgopolov.calculateworkingtime.di.components.*
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.view.fragments.MainFragment
import com.dolgopolov.calculateworkingtime.view.fragments.ProjectsFragment
import com.dolgopolov.calculateworkingtime.view.fragments.SettingFragment
import com.dolgopolov.calculateworkingtime.view.fragments.TimerFragment

class App : Application() {
    lateinit var appComponent: AppComponent
    lateinit var timerFragmentComponent: TimerFragmentComponent
    lateinit var mainFragmentComponent: MainFragmentComponent
    lateinit var projectsFragmentComponent: ProjectsFragmentComponent
    lateinit var settingFragmentComponent: SettingFragmentComponent


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