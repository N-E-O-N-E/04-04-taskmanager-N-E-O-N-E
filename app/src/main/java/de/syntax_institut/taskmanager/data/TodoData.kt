package de.syntax_institut.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_notes")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var todoTitle: String,
    var todoText: String,
    var date: String,
    var isDone: Boolean = false,
    var isArchived: Boolean = false,
)