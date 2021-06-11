package com.dolgopolov.calculateworkingtime.models

import java.util.*

data class DayInformation(
    val formattedDate: String,
    val listWorkingTime: List<WorkingTimeInformation> = emptyList(),
    val id: Int = Random().nextInt(),
    var isDayOff: Boolean = false
)
