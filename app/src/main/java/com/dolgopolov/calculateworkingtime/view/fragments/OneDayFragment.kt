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
import com.dolgopolov.calculateworkingtime.view.adapters.DayInfoHolder
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.OneDayFragmentViewModel
import java.net.DatagramPacket


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
        val dayViewHolder = DayInfoHolder()

        viewModel.dayInformation.observe(viewLifecycleOwner) {
            dayViewHolder.bind(
                binder.dayInfo.root,
                it,
                viewModel.getCountWorkingHoursInDay()
            )

            if (binder.switchIsDayOffView.switchIsDayOff.isChecked != it.isDayOff)
                binder.switchIsDayOffView.switchIsDayOff.isChecked = it.isDayOff
        }
    }
}