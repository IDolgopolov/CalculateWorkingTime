package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentOneDayBinding
import com.dolgopolov.calculateworkingtime.managers.DateParser
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.OneDayFragmentViewModel


class OneDayFragment : BaseFragment<FragmentOneDayBinding>() {
    private val args: OneDayFragmentArgs by navArgs()
    private val viewModel: OneDayFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one_day, container, false)
        _binder = FragmentOneDayBinding.bind(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        App.getInstance().oneDayFragmentComponent = App.getInstance()
            .appComponent
            .oneDayFragment()
            .build()
        App.getInstance().oneDayFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setDayInfo(args.dayInfo)
        observe()

        binder.switchIsDayOffView.switchIsDayOff.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDayOffStatus(args.dayInfo, isChecked)
        }
    }

    private fun observe() {
        viewModel.dayInformation.observe(viewLifecycleOwner) {
            binder.dayInfo.tvDate.text =
                DateParser.getDateNumberFromFormattedDate(it.formattedDate)
            binder.dayInfo.tvTime.text =
                DateParser.getWorkingTimeFormatted(it.listWorkingTime)
            if (binder.switchIsDayOffView.switchIsDayOff.isChecked != it.isDayOff)
                binder.switchIsDayOffView.switchIsDayOff.isChecked = it.isDayOff
        }
    }
}