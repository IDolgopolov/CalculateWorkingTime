package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentStatisticsBinding
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment


class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        _binder = FragmentStatisticsBinding.bind(view)
        return view
    }
}