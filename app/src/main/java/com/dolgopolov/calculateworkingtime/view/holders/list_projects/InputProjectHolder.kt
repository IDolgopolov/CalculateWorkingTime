package com.dolgopolov.calculateworkingtime.view.holders.list_projects

import android.view.View
import android.widget.EditText
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.view.holders.list_projects.ItemListProjectsHolder

class InputProjectHolder(itemView: View) : ItemListProjectsHolder(itemView) {

    fun bind(projectAddingListener: ProjectListener) {
        val etName = itemView.findViewById<EditText>(R.id.et_name)
        itemView.findViewById<View>(R.id.b_add).setOnClickListener {
            val name = etName.text.toString()
            etName.setText(String())
            projectAddingListener.onNewProjectEntered(name)
        }
    }
}