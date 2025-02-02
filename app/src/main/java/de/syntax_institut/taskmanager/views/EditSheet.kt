package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.GrayAshDark
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun EditSheet(
    modifier: Modifier,
    selectedTodo: Todo?,
    viewModel: TodoViewModel = viewModel(),
    onDismiss: () -> Unit
) {
    var newTitle by remember { mutableStateOf(selectedTodo?.todoTitle ?: "") }
    var newText by remember { mutableStateOf(selectedTodo?.todoText ?: "") }
    var newDate by remember { mutableStateOf(selectedTodo?.date ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrayAshDark)
    ) {
        Column {

            TextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                modifier = modifier
            )
            TextField(
                value = newText,
                onValueChange = { newText = it },
                modifier = modifier
            )
            TextField(
                value = newDate,
                onValueChange = { newDate = it },
                modifier = modifier
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.update(
                        Todo(
                            id = selectedTodo?.id ?: 0,
                            todoTitle = newTitle,
                            todoText = newText,
                            date = newDate,
                            isDone = selectedTodo?.isDone ?: false,
                        )
                    )
                    onDismiss
                }) {
                Text("Speichern")
            }
        }
    }
}
