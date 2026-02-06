package com.example.moblilkiprojekt.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val dao: TaskDao
) {
    fun observeAll(): Flow<List<Task>> = dao.observeAll()

    suspend fun getById(id: Long): Task? = dao.getById(id)

    suspend fun add(title: String, description: String, imageUri: String?) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        dao.insert(
            Task(
                title = trimmed,
                description = description.trim(),
                imageUri = imageUri
            )
        )
    }

    suspend fun setDone(task: Task, done: Boolean) {
        dao.update(task.copy(isDone = done))
    }

    suspend fun delete(task: Task) {
        dao.delete(task)
    }
}
