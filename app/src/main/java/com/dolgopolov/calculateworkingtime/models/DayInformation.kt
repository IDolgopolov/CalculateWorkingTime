package com.dolgopolov.calculateworkingtime.models

data class DayInformation(
    val formattedDate: String,
    val listWorkingTime: List<WorkingTimeInformation> = emptyList()
)
