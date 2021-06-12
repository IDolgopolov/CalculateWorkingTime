package com.dolgopolov.calculateworkingtime.view.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation

class ProjectEditTimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(workingTimeInformation: WorkingTimeInformation, onRequestEditTime: () -> Unit) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvWorkingTime = itemView.findViewById<TextView>(R.id.tv_time)

        tvName.text = workingTimeInformation.project.name

        tvWorkingTime.text = DateParser.getWorkingTimeFormatted(workingTimeInformation.seconds)
        tvWorkingTime.setOnClickListener {
            onRequestEditTime()
        }
    }
}