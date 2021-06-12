package com.dolgopolov.calculateworkingtime.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentOneDayBinding
import com.dolgopolov.calculateworkingtime.models.WorkingTimeInformation
import com.dolgopolov.calculateworkingtime.view.adapters.RecyclerListProjectsAdapter
import com.dolgopolov.calculateworkingtime.view.adapters.RecyclerProjectsInDayAdapter
import com.dolgopolov.calculateworkingtime.view.holders.DayInfoHolder
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.OneDayFragmentViewModel


class OneDayFragment : BaseFragment<FragmentOneDayBinding>() {
    private val args: OneDayFragmentArgs by navArgs()
    private val viewModel: OneDayFragmentViewModel by viewModels()
    private lateinit var recyclerProjectsInDayAdapter: RecyclerProjectsInDayAdapter

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

        createAdapter()
    }

    private fun createAdapter() {
        val context = context ?: return

        recyclerProjectsInDayAdapter = RecyclerProjectsInDayAdapter {
            showDialogEditTime(it, args.dayInfo.id)
        }

        val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(context, R.drawable.divider_recycler_view)!!
        )
        binder.rvProjectInDay.addItemDecoration(divider)

        binder.rvProjectInDay.adapter = recyclerProjectsInDayAdapter
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

            recyclerProjectsInDayAdapter.add(it.listWorkingTime)
        }
    }

    private fun showDialogEditTime(workingTimeInformation: WorkingTimeInformation, dayId: Int) {
        val context = context ?: return
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_input_new_working_time, null, false)

        AlertDialog.Builder(context)
            .setTitle("Новое время:")
            .setView(dialogView)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                val etNewTime = dialogView.findViewById<EditText>(R.id.et_new_time)
                val newTime = etNewTime.text.toString().toLongOrNull()
                if (newTime != null)
                    viewModel.update(workingTimeInformation, dayId, newTime)
            }
            .create()
            .show()

    }
}