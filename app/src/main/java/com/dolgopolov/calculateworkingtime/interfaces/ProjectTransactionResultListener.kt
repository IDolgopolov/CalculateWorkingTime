package com.dolgopolov.calculateworkingtime.interfaces

import com.dolgopolov.calculateworkingtime.models.Project

interface ProjectTransactionResultListener {
    fun onAdded(project: Project)
    fun onDeleted(project: Project)
    fun onSelected(project: Project)
}