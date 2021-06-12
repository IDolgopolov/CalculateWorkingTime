package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.ProjectFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view_models.ProjectsFragmentViewModel
import dagger.Subcomponent

@Subcomponent(modules = [ProjectFragmentViewModelModule::class])
interface ProjectsFragmentViewModelComponent {
    fun inject(viewModel: ProjectsFragmentViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ProjectsFragmentViewModelComponent
    }
}