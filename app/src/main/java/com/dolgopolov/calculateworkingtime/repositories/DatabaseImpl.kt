package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.room.Room
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.managers.ModelConverter
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import kotlinx.coroutines.*
import javax.inject.Singleton

class DatabaseImpl(context: Context) : AppDatabase {
    private lateinit var workingTimeDB: WorkingTimeDatabase

    init {
        getDB(context)
    }

    private fun getDB(context: Context) = GlobalScope.launch {
        workingTimeDB = Room.databaseBuilder(
            context,
            WorkingTimeDatabase::class.java, "working_time"
        ).build()
    }

    override suspend fun getDayInformationBy(formattedDate: String) =
        withContext(Dispatchers.IO) {
            ModelConverter.parse(
                workingTimeDB.workingTimeDao().getDayBy(formattedDate)
            )
        }

    override suspend fun addProject(project: Project) = withContext(Dispatchers.IO) {
        workingTimeDB.workingTimeDao().add(
            ModelConverter.parse(project)
        )
    }

    override suspend fun getAllProjects() = withContext(Dispatchers.IO) {
        workingTimeDB.workingTimeDao().getAllProjects().map { ModelConverter.parse(it) }
    }

    override suspend fun deleteProject(project: Project) = withContext(Dispatchers.IO) {
        workingTimeDB.workingTimeDao().delete(
            ModelConverter.parse(project)
        )
    }

    override suspend fun markAsDeleted(project: Project) = withContext(Dispatchers.IO) {
        project.isDeleted = true
        workingTimeDB.workingTimeDao().update(
            ModelConverter.parse(project)
        )
    }

    override suspend fun addWorkingInfo(workingTimeInformation: WorkingTimeInformation) {
        DateParser.splitWorkingTime(workingTimeInformation).forEach {
            workingTimeDB.workingTimeDao().add(ModelConverter.parse(it))
        }
    }

    override suspend fun updateDayInfo(dayInfo: DayInformation) {
        workingTimeDB.workingTimeDao().update(
            ModelConverter.parse(dayInfo)
        )
    }
}