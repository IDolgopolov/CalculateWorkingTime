package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.view.fragments.SettingFragment
import dagger.Subcomponent

@Subcomponent
interface SettingFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SettingFragmentComponent
    }

    fun inject(fragment: SettingFragment)
    fun buildViewModel(): SettingFragmentViewModelComponent.Builder

}