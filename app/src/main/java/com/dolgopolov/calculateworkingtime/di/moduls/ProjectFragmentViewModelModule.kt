package com.dolgopolov.calculateworkingtime.di.moduls

import com.dolgopolov.calculateworkingtime.repositories.DatabaseController
import dagger.Module
import dagger.Provides

@Module
class ProjectFragmentViewModelModule{
    @Provides
    fun getDatabase() = DatabaseController()
}