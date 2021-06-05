package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.room.Room
import com.dolgopolov.calculateworkingtime.managers.ModelConverter
import com.dolgopolov.calculateworkingtime.models.DayInformation
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
            return@withContext ModelConverter.parse(
                getDB(context).workingTimeDao().getDayBy(formattedDate)
            )
        }
}