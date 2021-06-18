package com.dolgopolov.calculateworkingtime.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DayInformation(
    val formattedDate: String,
    val listWorkingTime: List<WorkingTimeInformation> = emptyList(),
    val id: Int,
    var isDayOff: Boolean = false
) : java.io.Serializable
