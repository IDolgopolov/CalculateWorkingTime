package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentOneDayBinding
import com.dolgopolov.calculateworkingtime.view.BaseFragment


class OneDayFragment : BaseFragment<FragmentOneDayBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one_day, container, false)
    }

    companion object {
        fun newInstance() =
            OneDayFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}