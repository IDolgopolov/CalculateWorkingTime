package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.view.fragments.OneDayFragment
import dagger.Subcomponent

@Subcomponent
interface OneDayFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): OneDayFragmentComponent
    }

    fun inject(fragment: OneDayFragment)
    fun buildViewModel(): OneDayFragmentViewModelComponent.Builder
}