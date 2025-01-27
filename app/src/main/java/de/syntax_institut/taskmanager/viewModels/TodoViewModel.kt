package de.syntax_institut.taskmanager.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import de.syntax_institut.taskmanager.data.TodoData
import de.syntax_institut.taskmanager.data.todoSamples
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val _todos = MutableStateFlow(listOf<TodoData>())
    var todos = _todos.asStateFlow()


    // Create/Add
    fun createNote(data: TodoData) {
        _todos.value = _todos.value + data
    }

    // Read
    init {
        _todos.value = todoSamples
    }

    // DeleteAll
    fun deleteList() {
        _todos.value = emptyList()
    }

    // DeleteOneItem
    fun deleteNote(data: TodoData) {
        _todos.value = _todos.value - data
    }

}