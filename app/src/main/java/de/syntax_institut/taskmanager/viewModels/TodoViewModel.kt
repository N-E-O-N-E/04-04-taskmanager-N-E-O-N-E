package de.syntax_institut.taskmanager.viewModels

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.taskmanager.data.TodoData
import de.syntax_institut.taskmanager.data.todoSamples
import de.syntax_institut.taskmanager.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val DATASTORE_SORT_KEY = booleanPreferencesKey("sortList")

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = application.dataStore

    val getSortState: Flow<Boolean> = dataStore.data.map { it[DATASTORE_SORT_KEY] ?: false }
    fun saveSortState(value: Boolean) {
        viewModelScope.launch { dataStore.edit { it[DATASTORE_SORT_KEY] = value } }
    }

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