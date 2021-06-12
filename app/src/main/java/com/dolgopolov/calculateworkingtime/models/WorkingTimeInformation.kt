package com.dolgopolov.calculateworkingtime.models

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class WorkingTimeInformation(
    val project: Project,
    var seconds: Long,
    val id: Int = Random.nextInt()
)
