package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule
import com.dolgopolov.calculateworkingtime.di.moduls.TimerFragmentModule
import com.dolgopolov.calculateworkingtime.view.fragments.MainFragment
import dagger.Subcomponent


@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : MainFragmentComponent
    }

    fun inject(fragment: MainFragment)
    fun buildViewModel() : MainFragmentViewModelComponent.Builder
}