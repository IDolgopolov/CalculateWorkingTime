package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentMainBinding
import com.dolgopolov.calculateworkingtime.view.BaseFragment
import com.dolgopolov.calculateworkingtime.view.custom_view.CalendarView
import com.dolgopolov.calculateworkingtime.view_models.MainFragmentViewModel


class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var calendarView: CalendarView

    companion object {
        fun newInstance() = MainFragment().apply {
            arguments = Bundle().apply {

            }
        }
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
            calendarView = CalendarView(it)
            calendarView.init(binder.containerCalendar)
            calendarView.onPreviousMonthClick = {
                viewModel.decreaseCurrentMonth()
                viewModel.requestDays()
            }
            calendarView.onNextMonthClick = {
                viewModel.increaseCurrentMonth()
                viewModel.requestDays()
            }

            viewModel.getMonthName().observe(viewLifecycleOwner, { monthName ->
                calendarView.setMonthName(monthName)
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