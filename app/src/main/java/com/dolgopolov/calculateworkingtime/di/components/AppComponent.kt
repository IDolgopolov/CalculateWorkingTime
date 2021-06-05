package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.AppModule
import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MainFragmentModule::class])
interface AppComponent {
    fun mainFragment() : MainFragmentComponent.Factory
}