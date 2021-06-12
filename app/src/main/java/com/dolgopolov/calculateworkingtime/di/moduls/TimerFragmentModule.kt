package com.dolgopolov.calculateworkingtime.di.moduls

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class TimerFragmentModule(private val fragment: Fragment) {
    @Provides
    fun getFragment() = fragment
}