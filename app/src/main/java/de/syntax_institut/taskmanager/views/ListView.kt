package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.TodoData
import de.syntax_institut.taskmanager.ui.theme.PurpleGrey80
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun TodoListItem(
    modifier: Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    val todos by viewModel.todos.collectAsState()
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 40.dp)
                .padding(horizontal = 15.dp)
        ) {
            items(todos) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .requiredHeight(height = 60.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(PurpleGrey80),
                    elevation = cardElevation(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(it.title)
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {
                            viewModel.deleteNote(it)

                        }) {
                            Text("Löschen")
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Todo hinzufügen") }
            )
            Button(
                onClick = {
                    var newNote = TodoData(title = textInput)
                    viewModel.createNote(newNote)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Hinzufügen")
            }
        }
    }
}

