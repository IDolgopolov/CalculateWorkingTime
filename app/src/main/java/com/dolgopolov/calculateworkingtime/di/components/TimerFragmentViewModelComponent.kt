package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.TimerFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view_models.TimerFragmentViewModel
import dagger.Subcomponent

@Subcomponent(modules = [TimerFragmentViewModelModule::class])
interface TimerFragmentViewModelComponent {
    fun inject(viewModel: TimerFragmentViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build() : TimerFragmentViewModelComponent
    }
}