package com.dolgopolov.calculateworkingtime.models.db_models.room_relations

import androidx.room.Embedded
import androidx.room.Relation
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.DayEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.WorkingTimeEntity

data class DayWithWorkingTime(
    @Embedded val dayEntity: DayEntity,
    @Relation(
        entity = WorkingTimeEntity::class,
        parentColumn = "id",
        entityColumn = "dayId"
    )
    val listWorkingTimesWithProject: List<WorkingTimeWithProject>
)


data class WorkingTimeWithProject(
    @Embedded val workingTimeEntity: WorkingTimeEntity,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "id"
    )
    val project: ProjectEntity
)
