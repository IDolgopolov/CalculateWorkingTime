package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentSettingBinding
import com.dolgopolov.calculateworkingtime.repositories.SettingRepository
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.SettingFragmentViewModel


class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val viewModel: SettingFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().settingFragmentComponent = App.getInstance()
            .appComponent
            .settingsFragment()
            .build()
        App.getInstance().settingFragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        _binder = FragmentSettingBinding.bind(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillSetting()
    }

    private fun fillSetting() {
        val context = context ?: return
        val values = ArrayList<Int>().apply { for (i in 1..24) add(i) }
        binder.settingCountWorkingHours.spinner.adapter =
            ArrayAdapter(context, R.layout.item_setting_spinner, values).apply {
                setDropDownViewResource(R.layout.item_setting_drop_down_spinner)
            }


        val selectedValue = viewModel.getSetting(SettingRepository.WORKING_HOURS_IN_DAY)
        binder.settingCountWorkingHours.spinner.setSelection(values.indexOf(selectedValue))

        binder.settingCountWorkingHours.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedHoursCount = values[position]
                    viewModel.saveSetting(
                        SettingRepository.WORKING_HOURS_IN_DAY,
                        selectedHoursCount
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}