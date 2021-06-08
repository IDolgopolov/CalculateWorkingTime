package com.dolgopolov.calculateworkingtime.view.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dolgopolov.calculateworkingtime.R

open class BaseFragment<T : ViewBinding> : Fragment() {
    protected var _binder: T? = null
    protected val binder get() = _binder!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarVisibility(View.VISIBLE)
        setToolbarLabel()
        setToolbarBackButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binder = null
    }

    protected fun navigateTo(id: Int) {
        findNavController().navigate(id)
    }

    protected fun setToolbarVisibility(visibility: Int) {
        getToolbar()?.visibility = visibility
    }

    private fun setToolbarLabel() {
        getToolbar()?.findViewById<TextView>(R.id.tv_label)?.text =
            findNavController().currentDestination?.label
    }

    private fun getToolbar() = activity
        ?.findViewById<ViewGroup>(R.id.toolbar)

    private fun setToolbarBackButton() {
        getToolbar()?.findViewById<View>(R.id.b_back)?.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}