package com.dolgopolov.calculateworkingtime.di.components

import android.app.Application
import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule
import com.dolgopolov.calculateworkingtime.di.moduls.TimerFragmentModule
import com.dolgopolov.calculateworkingtime.view.base.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun mainFragment(): MainFragmentComponent.Builder
    fun timerFragment(): TimerFragmentComponent.Builder
    fun projectsFragment(): ProjectsFragmentComponent.Builder
    fun settingsFragment(): SettingFragmentComponent.Builder
    fun oneDayFragment(): OneDayFragmentComponent.Builder

    fun inject(app: App)
}