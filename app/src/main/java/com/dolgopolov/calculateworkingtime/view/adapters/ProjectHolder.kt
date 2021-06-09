package com.dolgopolov.calculateworkingtime.view.adapters

import android.view.View
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.models.Project

class ProjectHolder(itemView: View) : ItemListProjectsHolder(itemView) {
    fun bind(project: Project, projectListener: ProjectListener) {
        itemView.findViewById<TextView>(R.id.tv_name).text = project.name
        itemView.findViewById<View>(R.id.b_select).setOnClickListener {
            projectListener.onSelect(project)
        }
        itemView.findViewById<View>(R.id.b_delete).setOnClickListener {
            projectListener.onDelete(project)
        }
    }
}