package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.ui.theme.WhiteAsh
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    val todoList by viewModel.listEntries.collectAsState()
    val todoListSorted by viewModel.listEntriesSorted.collectAsState()
    val sortState by viewModel.getSortState.collectAsState(initial = false)

    LazyColumn(
        modifier = modifier
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        items(
            if (sortState) {
                todoListSorted.size
            } else {
                todoList.size
            }

        ) {
            val item = if (sortState) {
                todoListSorted[it]
            } else {
                todoList[it]
            }
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(WhiteAsh.copy(alpha = 0.7f)),
                elevation = cardElevation(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                        Text(
                            text = item.todoTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider(Modifier.padding(vertical = 5.dp))

                        Text(text = item.todoText, Modifier.padding(vertical = 5.dp))

                        HorizontalDivider(Modifier.padding(vertical = 5.dp))

                        Text(
                            text = "Erstellt am: ${item.date}",
                            fontSize = 10.sp
                        )
                    }

                    IconButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            viewModel.delete(item)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Trash"
                        )
                    }
                }
            }
        }
    }
}
