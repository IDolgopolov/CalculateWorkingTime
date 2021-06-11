package com.dolgopolov.calculateworkingtime.models

import kotlinx.serialization.Serializable

@Serializable
data class WorkingTimeInformation(
    val project: Project,
    val seconds: Long
)
