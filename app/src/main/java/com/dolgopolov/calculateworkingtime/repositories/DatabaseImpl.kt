package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.room.Room
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.managers.ModelConverter
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.repositories.room_dao.WorkingTimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseImpl(context: Context) : AppDatabase {
    private lateinit var workingTimeDB: WorkingTimeRepository

    companion object {
        const val DATABASE_NAME = "working_time"
    }


    init {
        getDB(context)
    }

    private fun getDB(context: Context) = GlobalScope.launch {
        workingTimeDB = Room.databaseBuilder(
            context,
            WorkingTimeDatabase::class.java,
            DATABASE_NAME
        ).build().workingTimeDao()
    }

    override suspend fun getDayInformationBy(formattedDate: String) =
        withContext(Dispatchers.IO) {
            val dayDB = workingTimeDB.getDayBy(formattedDate)
            ModelConverter.parse(dayDB)
        }

    override suspend fun addProject(project: Project) = withContext(Dispatchers.IO) {
        val projectDB = ModelConverter.parse(project)
        workingTimeDB.add(projectDB)
    }

    override suspend fun getAllProjects() = withContext(Dispatchers.IO) {
        workingTimeDB.getAllProjects().map { ModelConverter.parse(it) }
    }

    override suspend fun deleteProject(project: Project) = withContext(Dispatchers.IO) {
        val project1 = ModelConverter.parse(project)
        workingTimeDB.delete(project1)
    }

    override suspend fun markAsDeleted(project: Project) = withContext(Dispatchers.IO) {
        project.isDeleted = true
        workingTimeDB.update(ModelConverter.parse(project))
    }

    override suspend fun addWorkingInfo(info: WorkingTimeInformation) =
        withContext(Dispatchers.IO) {
            DateParser.splitWorkingTime(info).forEach {
                val dayInfo = ModelConverter.parse(it)
                workingTimeDB.add(dayInfo)
            }
        }

    override suspend fun updateDayInfo(info: DayInformation) = withContext(Dispatchers.IO) {
        val dayInfo = ModelConverter.parse(info)
        workingTimeDB.update(dayInfo)
    }

    override suspend fun updateWorkingInfo(info: WorkingTimeInformation, dayId: Int) =
        withContext(Dispatchers.IO) {
            val workingTime = ModelConverter.parse(info, dayId)
            workingTimeDB.update(workingTime)
        }
}