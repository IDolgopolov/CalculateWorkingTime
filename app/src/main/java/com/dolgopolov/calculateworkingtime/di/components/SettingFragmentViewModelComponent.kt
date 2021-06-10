package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.SettingFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view_models.SettingFragmentViewModel
import dagger.Subcomponent

@Subcomponent(modules = [SettingFragmentViewModelModule::class])
interface SettingFragmentViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : SettingFragmentViewModelComponent
    }

    fun inject(viewModel: SettingFragmentViewModel)
}