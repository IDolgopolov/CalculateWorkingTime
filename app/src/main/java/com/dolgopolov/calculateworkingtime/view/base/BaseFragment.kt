package com.dolgopolov.calculateworkingtime.view.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dolgopolov.calculateworkingtime.R
import com.google.android.material.snackbar.Snackbar

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

    protected fun navigateTo(action: NavDirections) {
        findNavController().navigate(action)
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
    }

    protected fun setToolbarVisibility(visibility: Int) {
        getToolbar()?.visibility = visibility
    }

    protected fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    protected fun showMessage(message: Int) {
        showMessage(getString(message))
    }

    protected fun observeToError(error: MutableLiveData<String?>) {
        error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showMessage(it)
                error.value = null
            }
        }
    }

    protected fun requestConfirmation(text: Int, onConfirm: () -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(text)
            .setPositiveButton(R.string.ok) { _, _ ->
                onConfirm()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->

            }
            .create()
            .show()
    }

    private fun setToolbarLabel() {
        getToolbar()?.findViewById<TextView>(R.id.tv_label)?.text =
            findNavController().currentDestination?.label
    }

    private fun getToolbar() = activity
        ?.findViewById<ViewGroup>(R.id.toolbar)

    private fun setToolbarBackButton() {
        getToolbar()?.findViewById<View>(R.id.b_back)?.setOnClickListener {
            popBackStack()
        }
    }
}