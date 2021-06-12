package com.dolgopolov.calculateworkingtime.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.databinding.FragmentProjectsBinding
import com.dolgopolov.calculateworkingtime.di.moduls.ProjectFragmentModule
import com.dolgopolov.calculateworkingtime.interfaces.ProjectAddingListener
import com.dolgopolov.calculateworkingtime.interfaces.ProjectListener
import com.dolgopolov.calculateworkingtime.interfaces.ProjectTransactionResultListener
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.view.adapters.RecyclerListProjectsAdapter
import com.dolgopolov.calculateworkingtime.view.base.App
import com.dolgopolov.calculateworkingtime.view.base.BaseFragment
import com.dolgopolov.calculateworkingtime.view_models.ProjectsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ProjectsFragment : BaseFragment<FragmentProjectsBinding>(), ProjectTransactionResultListener {
    private val viewModel: ProjectsFragmentViewModel by viewModels()
    private lateinit var adapter: RecyclerListProjectsAdapter

    companion object {
        const val RESULT_KEY = "selected_project"
        const val PROJECT_KEY = "project"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().projectsFragmentComponent = App.getInstance()
            .appComponent
            .projectsFragment()
            .requestModule(ProjectFragmentModule(this))
            .build()
        App.getInstance()
            .projectsFragmentComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        _binder = FragmentProjectsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerAdapter()
        observe()
    }

    private val projectAddingListener = object : ProjectAddingListener {
        override fun onAdded(name: String) {
            viewModel.add(name)
        }
    }
    private val projectListener = object : ProjectListener {
        override fun onSelect(project: Project) {
            viewModel.selected(project)
        }

        override fun onDelete(project: Project) {
            requestConfirmation(
                R.string.delete_project_or_not,
                onConfirm = {
                    viewModel.delete(project)
                })
        }
    }

    private fun createRecyclerAdapter() {
        adapter = RecyclerListProjectsAdapter(projectAddingListener, projectListener)

        context?.let {
            val divider = DividerItemDecoration(it, RecyclerView.VERTICAL)
            divider.setDrawable(
                ContextCompat.getDrawable(it, R.drawable.divider_recycler_view)!!
            )
            binder.recyclerView.addItemDecoration(divider)
        }
        binder.recyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.getProjects().observe(viewLifecycleOwner) {
            adapter.add(it)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            showMessage(error)
        }
    }

    override fun onAdded(project: Project) {
        adapter.add(project)
    }

    override fun onDeleted(project: Project) {
        adapter.delete(project)
    }

    override fun onSelected(project: Project) {
        setFragmentResult(
            RESULT_KEY, bundleOf(
                PROJECT_KEY to Json.encodeToString(project)
            )
        )
        popBackStack()
    }
}