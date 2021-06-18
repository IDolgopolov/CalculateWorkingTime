package com.dolgopolov.calculateworkingtime.view.holders.list_projects

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.view.holders.list_projects.ItemListProjectsHolder

class ProjectHolder(itemView: View) : ItemListProjectsHolder(itemView) {
    fun bind(project: Project, projectListener: ProjectListener) {
        itemView.findViewById<TextView>(R.id.tv_name).text = project.name

        val bDelete = itemView.findViewById<View>(R.id.b_delete)
        when (project.isDeleted) {
            true -> {
                bDelete.visibility = View.GONE
                itemView.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.deleted_project_background_color
                    )
                )
            }
            false -> {
                bDelete.visibility = View.VISIBLE
                bDelete.setOnClickListener {
                    projectListener.onDelete(project)
                }
                itemView.setOnClickListener {
                    projectListener.onSelect(project)
                }

                itemView.backgroundTintList = null
            }
        }

    }
}