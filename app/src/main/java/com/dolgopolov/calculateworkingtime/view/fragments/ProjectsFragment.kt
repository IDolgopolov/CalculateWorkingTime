package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentProjectsBinding
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment


class ProjectsFragment : BaseFragment<FragmentProjectsBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    companion object {

        fun newInstance() =
            ProjectsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}