package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view_models.MainFragmentViewModel
import dagger.Subcomponent

@Subcomponent(modules = [MainFragmentViewModelModule::class])
interface MainFragmentViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : MainFragmentViewModelComponent
    }
    fun inject(viewModel: MainFragmentViewModel)
}