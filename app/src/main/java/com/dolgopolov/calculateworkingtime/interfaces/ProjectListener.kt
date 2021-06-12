package com.dolgopolov.calculateworkingtime.interfaces

import com.dolgopolov.calculateworkingtime.models.Project

interface ProjectListener {
    fun onSelect(project: Project)
    fun onDelete(project: Project)
    fun onNewProjectEntered(name: String)
}