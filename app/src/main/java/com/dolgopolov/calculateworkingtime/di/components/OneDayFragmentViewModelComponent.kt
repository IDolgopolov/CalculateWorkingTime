package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.view_models.OneDayFragmentViewModel
import dagger.Subcomponent

@Subcomponent
interface OneDayFragmentViewModelComponent {
    fun inject(viewModel: OneDayFragmentViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): OneDayFragmentViewModelComponent
    }

}