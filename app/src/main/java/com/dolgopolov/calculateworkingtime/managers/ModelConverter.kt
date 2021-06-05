package com.dolgopolov.calculateworkingtime.managers

import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.models.db_models.room_entities.ProjectEntity
import com.dolgopolov.calculateworkingtime.models.db_models.room_relations.DayWithWorkingTime

object ModelConverter {

    fun parse(dayDB: DayWithWorkingTime?): DayInformation? {
        if (dayDB == null) return null
        return DayInformation(
            dayDB.dayEntity.formattedDate,
            dayDB.listWorkingTimesWithProject.map {
                WorkingTimeInformation(
                    parse(it.project),
                    it.workingTimeEntity.workingTime
                )
            }
        )
    }

    fun parse(projectDB: ProjectEntity): Project {
        return Project(projectDB.name)
    }
}