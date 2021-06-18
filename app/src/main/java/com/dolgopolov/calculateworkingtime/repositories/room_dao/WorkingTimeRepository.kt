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

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects() : List<ProjectEntity>

    @Insert
    suspend fun add(project: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(day: DayEntity)

    @Insert
    suspend fun add(workingTime: WorkingTimeEntity)

    @Update
    suspend fun update(dayEntity: DayEntity)

    @Update
    suspend fun update(workingTime: WorkingTimeEntity)

    @Update
    suspend fun update(project: ProjectEntity)

    @Delete
    suspend fun delete(project: ProjectEntity)

    suspend fun update(dayInfo: DayWithWorkingTime) {
        update(dayInfo.dayEntity)
        dayInfo.listWorkingTimesWithProject.forEach {
            update(it.workingTimeEntity)
        }
    }

    suspend fun add(dayInfo: DayWithWorkingTime) {
        add(dayInfo.dayEntity)
        dayInfo.listWorkingTimesWithProject.forEach {
            add(it.workingTimeEntity)
        }
    }
}