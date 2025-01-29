package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun TodoListItem(
    modifier: Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    var textInput by remember { mutableStateOf("") }
    val sortState by viewModel.getSortState.collectAsState(initial = false)

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                modifier = Modifier
                    .weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "ToDo App"
            )

            Text(
                modifier = Modifier,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                text = if (sortState) "Alphabetisch" else "Standard"
            )

            Switch(modifier = Modifier
                .padding(start = 15.dp),
                checked = sortState,
                onCheckedChange = {
                    viewModel.saveSortState(it)
                })
        }

        HorizontalDivider()

        ItemList(modifier = Modifier.weight(1f))

        HorizontalDivider()

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
                    viewModel.insert(Todo(todoText = textInput))
                    textInput = "" //Leeren
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(" ADD ")
            }
        }
    }
}

