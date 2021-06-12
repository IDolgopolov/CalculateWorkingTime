package com.dolgopolov.calculateworkingtime.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.view.holders.ProjectEditTimeHolder

class RecyclerProjectsInDayAdapter(private val onRequestEditTime: (WorkingTimeInformation) -> Unit) : RecyclerView.Adapter<ProjectEditTimeHolder>() {
    private val list = ArrayList<WorkingTimeInformation>()

    override fun onBindViewHolder(holder: ProjectEditTimeHolder, position: Int) {
        val item = list[position]
        holder.bind(item) {
            onRequestEditTime(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectEditTimeHolder {
        return ProjectEditTimeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project_edit_time, parent, false)
        )
    }

    override fun getItemCount() = list.size

    fun add(listWorkingInfo: List<WorkingTimeInformation>) {
        val startIndex = list.size
        list.addAll(listWorkingInfo)
        notifyItemRangeChanged(startIndex, listWorkingInfo.size)
    }
}