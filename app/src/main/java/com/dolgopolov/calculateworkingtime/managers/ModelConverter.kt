package com.dolgopolov.calculateworkingtime.managers

import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.DayEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.WorkingTimeEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.DayWithWorkingTime
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.WorkingTimeWithProject

object ModelConverter {

    fun parse(dayDB: DayWithWorkingTime?): DayInformation? {
        if (dayDB == null) return null
        return DayInformation(
            dayDB.dayEntity.formattedDate,
            dayDB.listWorkingTimesWithProject.map {
                WorkingTimeInformation(
                    parse(it.project),
                    it.workingTimeEntity.workingTime,
                    it.workingTimeEntity.id
                )
            }
        )
    }

    fun parse(
        workingInfo: WorkingTimeInformation,
        dayId: Int,
    ): WorkingTimeEntity {
        return WorkingTimeEntity(
            workingInfo.id,
            dayId,
            workingInfo.project.id,
            workingInfo.seconds
        )
    }

    fun parse(projectDB: ProjectEntity): Project {
        return Project(projectDB.id, projectDB.name, projectDB.isDeleted)
    }

    fun parse(project: Project): ProjectEntity {
        return ProjectEntity(project.id, project.name, project.isDeleted)
    }

    fun parse(dayInformation: DayInformation): DayWithWorkingTime {
        val dayDB = DayEntity(
            dayInformation.id,
            dayInformation.formattedDate,
            dayInformation.isDayOff
        )

        return DayWithWorkingTime(
            dayDB,
            dayInformation.listWorkingTime.map { workingTimeInfo ->
                val projectDB = parse(workingTimeInfo.project)

                WorkingTimeWithProject(
                    parse(workingTimeInfo, dayDB.id),
                    projectDB
                )
            }
        )
    }
}