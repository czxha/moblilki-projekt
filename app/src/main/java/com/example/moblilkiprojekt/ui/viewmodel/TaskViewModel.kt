package com.example.moblilkiprojekt.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moblilkiprojekt.data.AppDatabase
import com.example.moblilkiprojekt.data.Task
import com.example.moblilkiprojekt.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val db: AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "tasks.db"
    ).build()

    private val repo = TaskRepository(db.taskDao())

    val tasks: StateFlow<List<Task>> = repo.observeAll()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), emptyList())

    fun addTask(title: String, description: String, imageUri: String?) {
        viewModelScope.launch {
            repo.add(title, description, imageUri)
        }
    }

    fun toggleDone(task: Task) {
        viewModelScope.launch {
            repo.setDone(task, !task.isDone)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repo.delete(task)
        }
    }

    suspend fun getTaskById(id: Long): Task? = repo.getById(id)
}