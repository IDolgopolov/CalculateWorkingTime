package com.dolgopolov.calculateworkingtime.view

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T : ViewBinding> : Fragment() {
    protected var _binder: T? = null
    protected val binder get() = _binder!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binder = null
    }
}