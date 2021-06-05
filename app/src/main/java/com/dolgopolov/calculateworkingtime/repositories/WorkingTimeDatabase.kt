package com.dolgopolov.calculateworkingtime.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.DayEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.WorkingTimeEntity
import com.dolgopolov.calculateworkingtime.repositories.room_dao.WorkingTimeRepository

@Database(entities = [DayEntity::class, ProjectEntity::class, WorkingTimeEntity::class], version = 1)
abstract class WorkingTimeDatabase : RoomDatabase() {

    abstract fun workingTimeDao() : WorkingTimeRepository
}
