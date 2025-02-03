package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.R
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.Black
import de.syntax_institut.taskmanager.ui.theme.DarkRed
import de.syntax_institut.taskmanager.ui.theme.GrayAsh
import de.syntax_institut.taskmanager.ui.theme.GrayAshDark
import de.syntax_institut.taskmanager.ui.theme.PanelColor
import de.syntax_institut.taskmanager.ui.theme.White
import de.syntax_institut.taskmanager.viewModels.TodoViewModel

@Composable
fun ArchiveSheet(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel(),
    onDismiss: () -> Unit = {},
) {
    val todoListArchived by viewModel.listArchivedEntries.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrayAshDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.japagirl),
            contentScale = ContentScale.Crop,
            contentDescription = "Wallpaper",
            alpha = 0.20f,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Gesamtes Archiv löschen!", color = White, fontSize = 15.sp)
                IconButton(
                    modifier = Modifier.scale(1.5f),
                    onClick = {
                        viewModel.deleteArchived(todoListArchived)
                        onDismiss()
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                        contentDescription = "Close",
                        colorFilter = ColorFilter.tint(White)
                    )
                }
            }

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 10.dp)
            ) {
                items(todoListArchived.size) {
                    val item = todoListArchived[it]

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(PanelColor.copy(alpha = 0.85f)),
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
                                    style = Typography().titleMedium, fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                                HorizontalDivider(
                                    Modifier.padding(vertical = 5.dp),
                                    color = GrayAsh
                                )

                                Text(
                                    text = item.todoText,
                                    style = Typography().bodyLarge,
                                    color = Black
                                )

                                HorizontalDivider(
                                    Modifier.padding(vertical = 5.dp),
                                    color = GrayAsh
                                )

                                Row {
                                    Text(
                                        text = "Erstellt am: ${item.date}",
                                        style = Typography().labelSmall,
                                        color = Black
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = if (item.isArchived) "Entgültig löschen" else " ",
                                        style = Typography().labelSmall,
                                        color = DarkRed,
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
                                            isArchived = false
                                        )
                                        viewModel.update(newItem)
                                    }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_unarchive_24),
                                        contentDescription = "Unarchive",
                                        colorFilter = ColorFilter.tint(GrayAsh),
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
