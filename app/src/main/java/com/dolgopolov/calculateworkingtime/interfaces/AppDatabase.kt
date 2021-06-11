package com.dolgopolov.calculateworkingtime.interfaces

import com.dolgopolov.calculateworkingtime.models.DayInformation
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation

interface AppDatabase {
    suspend fun getDayInformationBy(formattedDate: String) : DayInformation?
    suspend fun addProject(project: Project)
    suspend fun getAllProjects() : List<Project>
    suspend fun deleteProject(project: Project)
    suspend fun addWorkingInfo(workingTimeInformation: WorkingTimeInformation)
    suspend fun updateDayInfo(dayInfo: DayInformation)
}