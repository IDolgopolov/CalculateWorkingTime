package com.dolgopolov.calculateworkingtime.repositories.room_dao

import androidx.room.*
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.DayWithWorkingTime

@Dao
interface WorkingTimeRepository {
    @Transaction
    @Query("SELECT * FROM days")
    suspend fun getAllDays() : List<DayWithWorkingTime>

    @Transaction
    @Query("SELECT * FROM days WHERE formattedDate = :date")
    suspend fun getDayBy(date: String) : DayWithWorkingTime?

    @Insert
    suspend fun add(project: ProjectEntity)

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects() : List<ProjectEntity>

    @Delete
    suspend fun delete(project: ProjectEntity)
}