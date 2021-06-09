package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.ProjectFragmentModule
import com.dolgopolov.calculateworkingtime.di.moduls.ProjectFragmentViewModelModule
import com.dolgopolov.calculateworkingtime.view.fragments.ProjectsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ProjectFragmentModule::class])
interface ProjectsFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: ProjectFragmentModule) : Builder
        fun build() : ProjectsFragmentComponent
    }

    fun inject(fragment: ProjectsFragment)
    fun buildViewModel() : ProjectsFragmentViewModelComponent.Builder
}