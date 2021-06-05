package com.dolgopolov.calculateworkingtime.models.db_models.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "working_times")
data class WorkingTimeEntity(
    @PrimaryKey val id: Int,
    val dayId: Int,
    val projectId: Int,
    val workingTime: Long
)
