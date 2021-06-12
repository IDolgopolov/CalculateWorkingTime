package com.dolgopolov.calculateworkingtime.view.holders

import android.view.View
import android.widget.TextView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.view.holders.ItemListProjectsHolder

class ProjectHolder(itemView: View) : ItemListProjectsHolder(itemView) {
    fun bind(project: Project, projectListener: ProjectListener) {
        itemView.findViewById<TextView>(R.id.tv_name).text = project.name

        val bDelete = itemView.findViewById<View>(R.id.b_delete)
        when (project.isDeleted) {
            true -> {
                bDelete.visibility = View.GONE
            }
            false -> {
                bDelete.visibility = View.VISIBLE
                bDelete.setOnClickListener {
                    projectListener.onDelete(project)
                }
                itemView.setOnClickListener {
                    projectListener.onSelect(project)
                }
            }
        }

    }
}