package com.dolgopolov.calculateworkingtime.models.db_models.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey val id: Int,
    val formattedDate: String,
    val isDayOff: Boolean
)