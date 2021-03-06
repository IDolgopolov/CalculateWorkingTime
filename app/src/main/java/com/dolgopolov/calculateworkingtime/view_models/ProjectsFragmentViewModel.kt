package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.*
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.AppDatabase
import com.dolgopolov.calculateworkingtime.interfaces.ProjectTransactionResultListener
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class ProjectsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val listProjects = MutableLiveData(ArrayList<Project>())
    val error = MutableLiveData<String?>()

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var projectTransactionListener: ProjectTransactionResultListener

    init {
        App.getInstance()
            .projectsFragmentComponent
            .buildViewModel()
            .build()
            .inject(this)
    }


    fun add(name: String) = viewModelScope.launch {
        if (name.isEmpty()) {
            val errorMessage = getApplication<Application>().getString(R.string.error_empty_name)
            error.value = errorMessage
            return@launch
        }

        val project = Project(Random.nextInt(), name, false)
        database.addProject(project)
        listProjects.value!!.add(project)
        projectTransactionListener.onAdded(project)
    }

    fun getProjects(): LiveData<ArrayList<Project>> {
        if (listProjects.value!!.size == 0)
            requestProjectsFromBD()
        return listProjects
    }

    fun delete(project: Project) = viewModelScope.launch {
        database.markAsDeleted(project)
        listProjects.value!!.remove(project)
        projectTransactionListener.onDeleted(project)
    }

    fun selected(project: Project) {
        projectTransactionListener.onSelected(project)
    }

    private fun requestProjectsFromBD() = viewModelScope.launch {
        val list = database.getAllProjects()
        listProjects.value = ArrayList(list)
    }
}