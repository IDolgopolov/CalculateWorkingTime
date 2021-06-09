package com.dolgopolov.calculateworkingtime.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectAddingListener
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.models.Project
import java.lang.IllegalStateException

class RecyclerListProjectsAdapter(
    private val projectAddingListener: ProjectAddingListener,
    private val projectListener: ProjectListener
) : RecyclerView.Adapter<ItemListProjectsHolder>() {

    private val items = ArrayList<Project>()

    override fun onBindViewHolder(holder: ItemListProjectsHolder, position: Int) {
        when (getItemViewType(position)) {
            HOLDER_TYPE_PROJECT -> {
                val item = items[position]
                (holder as ProjectHolder).bind(item, projectListener)
            }
            HOLDER_TYPE_INPUT_PROJECT -> {
                (holder as InputProjectHolder).bind(projectAddingListener)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListProjectsHolder {
        val resource = when (viewType) {
            HOLDER_TYPE_PROJECT -> R.layout.item_project
            HOLDER_TYPE_INPUT_PROJECT -> R.layout.item_input_project
            else -> throw IllegalStateException()
        }
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)

        return when (viewType) {
            HOLDER_TYPE_PROJECT -> ProjectHolder(view)
            HOLDER_TYPE_INPUT_PROJECT -> InputProjectHolder(view)
            else -> throw IllegalStateException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size + INPUT_ITEMS_COUNT - 1)
            HOLDER_TYPE_INPUT_PROJECT
        else
            HOLDER_TYPE_PROJECT
    }

    override fun getItemCount() = items.size + INPUT_ITEMS_COUNT

    private companion object {
        const val HOLDER_TYPE_PROJECT = 1
        const val HOLDER_TYPE_INPUT_PROJECT = 2
        const val INPUT_ITEMS_COUNT = 1
    }

    fun add(project: Project) {
        items.add(project)
        notifyItemInserted(items.size - 1)
    }

    fun add(projects: List<Project>) {
        val startIndex = items.size
        items.addAll(projects)
        notifyItemRangeChanged(startIndex, projects.size)
    }

    fun delete(project: Project) {
        val index = items.indexOf(project)
        items.remove(project)
        notifyItemRemoved(index)
    }
}