package com.dolgopolov.calculateworkingtime.repositories.room_dao

import androidx.room.*
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.DayEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.WorkingTimeEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.DayWithWorkingTime

@Dao
interface WorkingTimeRepository {
    @Transaction
    @Query("SELECT * FROM days")
    suspend fun getAllDays() : List<DayWithWorkingTime>

    @Transaction
    @Query("SELECT * FROM days WHERE formattedDate = :date")
    suspend fun getDayBy(date: String) : DayWithWorkingTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(project: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(day: DayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(workingTime: WorkingTimeEntity)

    suspend fun add(dayInfo: DayWithWorkingTime) {
        add(dayInfo.dayEntity)
        dayInfo.listWorkingTimesWithProject.forEach {
            add(it.workingTimeEntity)
        }
    }

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects() : List<ProjectEntity>

    @Delete
    suspend fun delete(project: ProjectEntity)
}