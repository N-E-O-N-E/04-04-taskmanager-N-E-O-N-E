package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.R
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.GrayAsh
import de.syntax_institut.taskmanager.ui.theme.PanelColor
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel(),
    searchQuery: String,
    openEditSheet: (item: Todo) -> Unit,
) {
    val todoList by viewModel.listEntries.collectAsState()
    val todoListSorted by viewModel.listEntriesSorted.collectAsState()
    val sortState by viewModel.getSortState.collectAsState(initial = false)

    val filteredList = if (searchQuery.isBlank()) {
        if (sortState) todoListSorted else todoList
    } else {
        (if (sortState) todoListSorted else todoList).filter {
            it.todoTitle.contains(searchQuery, ignoreCase = true) ||
                    it.todoText.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        items(filteredList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(PanelColor.copy(alpha = 0.80f)),
                elevation = cardElevation(),
                onClick = { openEditSheet(item) },
                ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                        Text(
                            text = item.todoTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider(
                            Modifier.padding(vertical = 5.dp),
                            color = GrayAsh.copy(alpha = 0.5f)
                        )

                        Text(text = item.todoText, Modifier.padding(vertical = 5.dp))

                        HorizontalDivider(
                            Modifier.padding(vertical = 5.dp),
                            color = GrayAsh.copy(alpha = 0.5f)
                        )

                        Row {
                            Text(
                                text = "Erstellt am: ${item.date}",
                                fontSize = 12.sp
                            )
                        }
                    }

                    Column {
                        IconButton(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                val newItem = Todo(
                                    id = item.id,
                                    todoTitle = item.todoTitle,
                                    todoText = item.todoText,
                                    date = item.date,
                                    isDone = item.isDone,
                                    isArchived = true
                                )
                                viewModel.update(newItem)
                            }
                        ) {
                            Image(
                                modifier = Modifier.scale(1.0f),
                                painter = painterResource(id = R.drawable.baseline_archive_24),
                                contentDescription = "Archive",
                                colorFilter = ColorFilter.tint(GrayAsh),
                            )
                        }
                    }
                }
            }
        }
    }
}
