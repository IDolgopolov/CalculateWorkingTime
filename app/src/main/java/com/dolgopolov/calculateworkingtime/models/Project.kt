package com.dolgopolov.calculateworkingtime.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Int,
    val name: String,
    var isDeleted: Boolean
)