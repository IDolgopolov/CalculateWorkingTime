package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.TimerFragmentModule
import com.dolgopolov.calculateworkingtime.view.fragments.TimerFragment
import dagger.Subcomponent

@Subcomponent(modules = [TimerFragmentModule::class])
interface TimerFragmentComponent {


    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: TimerFragmentModule): Builder
        fun build(): TimerFragmentComponent
    }

    fun inject(fragment: TimerFragment)
    fun buildViewModel(): TimerFragmentViewModelComponent.Builder
}