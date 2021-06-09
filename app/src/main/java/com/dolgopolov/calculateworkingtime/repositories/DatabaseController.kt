package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.room.Room
import com.dolgopolov.calculateworkingtime.managers.ModelConverter
import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DatabaseController {
    private var workingTimeDB: WorkingTimeDatabase? = null

    private fun getDB(context: Context): WorkingTimeDatabase {
        if (workingTimeDB != null) return workingTimeDB!!

        workingTimeDB = Room.databaseBuilder(
            context,
            WorkingTimeDatabase::class.java, "working_time"
        ).build()

        return workingTimeDB!!
    }

    suspend fun getDayInformationBy(formattedDate: String, context: Context) =
        withContext(Dispatchers.IO) {
            ModelConverter.parse(
                getDB(context).workingTimeDao().getDayBy(formattedDate)
            )
        }

    suspend fun addProject(project: Project, context: Context) = withContext(Dispatchers.IO) {
        getDB(context).workingTimeDao().add(
            ModelConverter.parse(project)
        )
    }

    suspend fun getAllProjects(context: Context) = withContext(Dispatchers.IO) {
        getDB(context).workingTimeDao().getAllProjects().map { ModelConverter.parse(it) }
    }

    suspend fun deleteProject(project: Project, context: Context) = withContext(Dispatchers.IO) {
        getDB(context).workingTimeDao().delete(
            ModelConverter.parse(project)
        )
    }
}