package com.dolgopolov.calculateworkingtime.models.db_models.room_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val isDeleted: Boolean
)
