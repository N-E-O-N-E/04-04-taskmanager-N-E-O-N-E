package de.syntax_institut.taskmanager.viewModels

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.data.TodoDatabase
import de.syntax_institut.taskmanager.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val DATASTORE_SORT_KEY = booleanPreferencesKey("sortList")

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // Datenbank
    private val dao = TodoDatabase.getDatabase(application.applicationContext).toDoDatabase()

    val listEntries = dao.getAllItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val listEntriesSorted = dao.getAllItemsSorted().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val listArchivedEntries = dao.getAllArchivedItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun delete(todo: Todo) {
        viewModelScope.launch { dao.delete(todo) }
    }

    fun deleteArchived(listArchivedEntries: List<Todo>) {
        viewModelScope.launch {
            for (item in listArchivedEntries) {
                if (item.isArchived) {
                    dao.delete(item)
                }
            }
        }
    }

    fun insert(todo: Todo) {
        viewModelScope.launch { dao.insert(todo) }
    }

    fun update(todo: Todo) {
        viewModelScope.launch { dao.update(todo) }
    }

    // Sortieren
    private val dataStore = application.dataStore

    val getSortState: Flow<Boolean> = dataStore.data.map { it[DATASTORE_SORT_KEY] ?: false }
    fun saveSortState(value: Boolean) {
        viewModelScope.launch { dataStore.edit { it[DATASTORE_SORT_KEY] = value } }
    }
}