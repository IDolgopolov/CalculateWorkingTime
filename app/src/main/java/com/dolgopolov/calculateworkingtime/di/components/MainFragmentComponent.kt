package com.dolgopolov.calculateworkingtime.di.components

import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule
import com.dolgopolov.calculateworkingtime.view.custom_view.CalendarView
import com.dolgopolov.calculateworkingtime.view.fragments.MainFragment
import com.dolgopolov.calculateworkingtime.view_models.MainFragmentViewModel
import dagger.Component
import dagger.Subcomponent

@Subcomponent
interface MainFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : MainFragmentComponent
    }

    fun inject(fragment: MainFragment)
}