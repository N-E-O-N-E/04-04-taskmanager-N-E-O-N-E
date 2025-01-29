package de.syntax_institut.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_todoList")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var todoText: String
)