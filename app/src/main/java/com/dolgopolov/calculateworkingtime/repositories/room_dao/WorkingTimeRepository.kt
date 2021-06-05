package com.dolgopolov.calculateworkingtime.repositories.room_dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.DayWithWorkingTime

@Dao
interface WorkingTimeRepository {
    @Transaction
    @Query("SELECT * FROM days")
    suspend fun getAllDays() : List<DayWithWorkingTime>

    @Transaction
    @Query("SELECT * FROM days WHERE formattedDate = :date")
    suspend fun getDayBy(date: String) : DayWithWorkingTime?
}