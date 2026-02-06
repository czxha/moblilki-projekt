package com.example.moblilkiprojekt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val isDone: Boolean = false,
    val imageUri: String? = null,   // multimedia (opcjonalnie)
    val createdAt: Long = System.currentTimeMillis()
)
