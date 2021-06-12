package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentMainBinding
import com.dolgopolov.calculateworkingtime.di.components.AppComponent
import com.dolgopolov.calculateworkingtime.di.moduls.MainFragmentModule
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view.custom_view.CalendarView
import com.dolgopolov.calculateworkingtime.view_models.MainFragmentViewModel
import java.lang.Exception
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val viewModel: MainFragmentViewModel by viewModels()

    @Inject
    lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().mainFragmentComponent = App.getInstance()
            .appComponent
            .mainFragment()
            .build()
        App.getInstance().mainFragmentComponent.inject(this)
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

        setToolbarVisibility(View.GONE)

        initCalendar()
        observe()
        setListeners()
    }

    private fun setListeners() {
        binder.bSetting.root.setOnClickListener {
            navigateTo(R.id.action_mainFragment_to_settingFragment)
        }

        binder.bTimer.root.setOnClickListener {
            navigateTo(R.id.action_mainFragment_to_timerFragment)
        }
    }

    private fun observe() {
        viewModel.getMonthName().observe(viewLifecycleOwner, { monthName ->
            calendarView.setMonthAndYearDate(monthName)
        })
        viewModel.getDays().observe(viewLifecycleOwner, { days ->
            calendarView.setDays(days, viewModel.getCountWorkingHoursInDay())
        })
    }

    private fun initCalendar() {
        calendarView.init(binder.containerCalendar)
        calendarView.onPreviousMonthClick = {
            viewModel.requestDays(MainFragmentViewModel.DECREASE_MONTH)
        }
        calendarView.onNextMonthClick = {
            viewModel.requestDays(MainFragmentViewModel.INCREASE_MONTH)
        }
        calendarView.onDaySelected = { dayInfo ->
            val action = MainFragmentDirections.actionMainFragmentToOneDayFragment(dayInfo)
            navigateTo(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        calendarView.onDestroyView()
    }
}