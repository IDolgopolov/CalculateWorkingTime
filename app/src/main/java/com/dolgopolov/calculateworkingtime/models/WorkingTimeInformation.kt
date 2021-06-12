package com.dolgopolov.calculateworkingtime.models

import kotlinx.serialization.Serializable

@Serializable
data class WorkingTimeInformation(
    val id: Int,
    val project: Project,
    var seconds: Long
)
