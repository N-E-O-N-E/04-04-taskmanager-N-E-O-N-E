package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.R
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.Blue
import de.syntax_institut.taskmanager.ui.theme.DarkRed
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
            painter = painterResource(id = R.drawable.wallpaper),
            contentScale = ContentScale.Crop,
            contentDescription = "Wallpaper",
            alpha = 0.3f,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Gesamtes Archiv löschen!", color = Color.Black, fontSize = 15.sp)
                IconButton(
                    modifier = Modifier.scale(1.5f),
                    onClick = {
                    viewModel.deleteArchived(todoListArchived)
                    onDismiss()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                        contentDescription = "Close",
                        colorFilter = ColorFilter.tint(DarkRed)
                    )
                }
            }

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
                        colors = CardDefaults.cardColors(WhiteAsh.copy(alpha = 0.8f)),
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
                                    fontSize = 12.sp, color = DarkRed,
                                )
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
                                            isArchived = false
                                        )
                                        viewModel.update(newItem)
                                    }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_unarchive_24),
                                        contentDescription = "Unarchive",
                                        colorFilter = ColorFilter.tint(Blue),
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
                                        contentDescription = "Trash",
                                        tint = DarkRed
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
