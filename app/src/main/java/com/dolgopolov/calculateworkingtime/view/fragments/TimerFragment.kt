package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentTimerBinding
import com.dolgopolov.calculateworkingtime.di.moduls.TimerFragmentModule
import com.dolgopolov.calculateworkingtime.states.TimerState
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.TimerFragmentViewModel

class TimerFragment : BaseFragment<FragmentTimerBinding>() {
    private val viewModel: TimerFragmentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().timerFragmentComponent = App.getInstance().appComponent
            .timerFragment()
            .requestModule(TimerFragmentModule(this))
            .build()
        App.getInstance().timerFragmentComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        _binder = FragmentTimerBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        bind()

        setFragmentResultListener(ProjectsFragment.RESULT_KEY) { _, bundle ->
            viewModel.decodeSelectedProject(bundle)
        }
    }

    private fun setListeners() {
        binder.bTimerPlayPause.root.setOnClickListener {
            viewModel.playPauseTimer()
        }

        binder.bTimerStop.root.setOnClickListener {
            viewModel.stopTimer()
        }

        binder.bSelectProject.root.setOnClickListener {
            navigateTo(R.id.action_timerFragment_to_projectsFragment)
        }
    }

    private fun bind() {
        viewModel.isTimerStarted.observe(viewLifecycleOwner) { timerState ->
            when (timerState) {
                TimerState.Playing -> {
                    binder.bTimerStop.root.visibility = View.VISIBLE
                    binder.bTimerPlayPause.root.setImageResource(R.drawable.ic_pause)
                }
                TimerState.Paused -> {
                    binder.bTimerStop.root.visibility = View.VISIBLE
                    binder.bTimerPlayPause.root.setImageResource(R.drawable.ic_play)
                }
                TimerState.Stopped -> {
                    binder.bTimerStop.root.visibility = View.GONE
                    binder.bTimerPlayPause.root.setImageResource(R.drawable.ic_play)
                }
            }
        }

        viewModel.timePassedFormatted.observe(viewLifecycleOwner) {
            binder.tvTimePassed.text = it
        }

        viewModel.selectedProject.observe(viewLifecycleOwner) {
            binder.bSelectProject.root.text = StringBuilder()
                .append(getString(R.string.project))
                .append(getString(R.string.space))
                .append(it.name)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showMessage(it)
                viewModel.error.value = null
            }
        }

        viewModel.getTodayDate().observe(viewLifecycleOwner) {
            binder.tvTodayDate.text = StringBuilder()
                .append(getString(R.string.today_date))
                .append(getString(R.string.line_break))
                .append(it)
                .toString()
        }
    }


}