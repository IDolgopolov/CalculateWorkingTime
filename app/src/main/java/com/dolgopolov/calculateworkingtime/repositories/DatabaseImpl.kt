package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.room.Room
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.managers.ModelConverter
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseImpl(context: Context) : AppDatabase {
    private val workingTimeDB: WorkingTimeDatabase

    init {
        workingTimeDB = getDB(context)
    }

    private fun getDB(context: Context): WorkingTimeDatabase {
        return Room.databaseBuilder(
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

    override suspend fun addWorkingInfo(workingTimeInformation: WorkingTimeInformation) {
        val dao = workingTimeDB.workingTimeDao()
        DateParser.splitWorkingTime(workingTimeInformation).forEach {
            dao.add(ModelConverter.parse(it))
        }
    }
}