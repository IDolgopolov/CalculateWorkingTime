package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentMainBinding
import com.dolgopolov.calculateworkingtime.view.App
import com.dolgopolov.calculateworkingtime.view.BaseFragment
import com.dolgopolov.calculateworkingtime.view.custom_view.CalendarView
import com.dolgopolov.calculateworkingtime.view_models.MainFragmentViewModel
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding>() {
    @Inject lateinit var viewModel: MainFragmentViewModel
    @Inject lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().appComponent.mainFragment().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        _binder = FragmentMainBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.also {
            calendarView.init(binder.containerCalendar)
            calendarView.onPreviousMonthClick = {
                viewModel.requestDays(MainFragmentViewModel.DECREASE_MONTH)
            }
            calendarView.onNextMonthClick = {
                viewModel.requestDays(MainFragmentViewModel.INCREASE_MONTH)
            }

            viewModel.getMonthName().observe(viewLifecycleOwner, { monthName ->
                calendarView.setMonthAndYearDate(monthName)
            })
            viewModel.getDays().observe(viewLifecycleOwner, { days ->
                calendarView.setDays(days)
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        calendarView.onDestroyView()
    }
}