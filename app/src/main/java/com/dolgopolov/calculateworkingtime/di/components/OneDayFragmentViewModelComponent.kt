package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.ProjectFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view_models.OneDayFragmentViewModel
import com.dolgopolov.calculateworkingtime.view_models.ProjectsFragmentViewModel
import dagger.Subcomponent

@Subcomponent
interface OneDayFragmentViewModelComponent {
    fun inject(viewModel: OneDayFragmentViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build() : OneDayFragmentViewModelComponent
    }

}