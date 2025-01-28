package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.TodoData
import de.syntax_institut.taskmanager.ui.theme.PurpleGrey80
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    todos: List<TodoData>,
    viewModel: TodoViewModel = viewModel()
) {
    val sortState by viewModel.getSortState.collectAsState(initial = false)

    LazyColumn(
        modifier = modifier
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        items(if (sortState) todos.sortedBy { it.title } else todos) {
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

                    Text(it.title, modifier = modifier.weight(1f))


                    IconButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = { viewModel.deleteNote(it) }
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Trash")
                    }
                }
            }
        }
    }
}