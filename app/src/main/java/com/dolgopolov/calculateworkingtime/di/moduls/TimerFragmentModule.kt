package com.dolgopolov.calculateworkingtime.di.moduls

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.di.components.TimerFragmentComponent
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TimerFragmentModule(private val fragment: Fragment) {

    @Provides
    fun getFragment() = fragment
}