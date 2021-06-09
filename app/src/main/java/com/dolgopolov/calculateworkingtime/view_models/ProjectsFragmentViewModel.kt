package com.dolgopolov.calculateworkingtime.view_models

import android.app.Application
import androidx.lifecycle.*
import com.dolgopolov.calculateworkingtime.R
import com.dolgopolov.calculateworkingtime.interfaces.ProjectTransactionResultListener
import com.dolgopolov.calculateworkingtime.models.Project
import com.dolgopolov.calculateworkingtime.repositories.DatabaseController
import com.dolgopolov.calculateworkingtime.view.base.App
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class ProjectsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val listProjects = MutableLiveData(ArrayList<Project>())
    val error = MutableLiveData<String>()

    @Inject
    lateinit var database: DatabaseController

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
        if(name.isEmpty()) {
            error.value = getApplication<Application>().getString(R.string.error_empty_name)
            return@launch
        }

        val project = Project(Random.nextInt(), name)
        database.addProject(project, getApplication())
        listProjects.value!!.add(project)
        projectTransactionListener.onAdded(project)
    }

    fun getProjects(): LiveData<ArrayList<Project>> {
        if (listProjects.value!!.size == 0)
            requestProjectsFromBD()
        return listProjects
    }

    fun delete(project: Project) = viewModelScope.launch {
        database.deleteProject(project, getApplication())
        listProjects.value!!.remove(project)
        projectTransactionListener.onDeleted(project)
    }

    fun selected(project: Project) {
        projectTransactionListener.onSelected(project)
    }

    private fun requestProjectsFromBD() = viewModelScope.launch {
        val list = database.getAllProjects(getApplication())
        listProjects.value = ArrayList(list)
    }
}