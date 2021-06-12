package com.dolgopolov.calculateworkingtime.di.moduls

import com.dolgopolov.calculateworkingtime.interfaces.ProjectTransactionResultListener
import dagger.Module
import dagger.Provides

@Module
class ProjectFragmentModule(private val projectTransactionListener: ProjectTransactionResultListener) {
    @Provides
    fun getListener() = projectTransactionListener
}