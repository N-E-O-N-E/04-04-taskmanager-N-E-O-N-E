package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.R
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.WhiteAsh
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun ArchiveSheet(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: TodoViewModel = viewModel(),
    onDismiss: () -> Unit = {},
) {
    val todoListArchived by viewModel.listArchivedEntries.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wall2),
            contentScale = ContentScale.Crop,
            contentDescription = "Wallpaper",
            alpha = 0.4f,
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 15.dp)
        ) {
            items(todoListArchived.size) {
                val item = todoListArchived[it]

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(5.dp),
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
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            HorizontalDivider(Modifier.padding(vertical = 5.dp))

                            Text(text = item.todoText, Modifier.padding(vertical = 5.dp))

                            HorizontalDivider(Modifier.padding(vertical = 5.dp))

                            Text(
                                text = "Erstellt am: ${item.date}",
                                fontSize = 12.sp
                            )
                            Text(
                                text = if (item.isArchived) "Archivierte Nachricht" else " ",
                                fontSize = 12.sp
                            )
                        }

                        Column {
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
                                    painter = painterResource(id = R.drawable.baseline_archive_24),
                                    contentDescription = "Archive"
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
