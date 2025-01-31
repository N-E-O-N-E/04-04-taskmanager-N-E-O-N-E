package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import de.syntax_institut.taskmanager.ui.theme.ArchivIcon
import de.syntax_institut.taskmanager.ui.theme.Blue
import de.syntax_institut.taskmanager.ui.theme.White
import de.syntax_institut.taskmanager.viewModels.TodoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@Composable
fun TodoList(
    modifier: Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    var textInput by remember { mutableStateOf("") }
    var searchTextInput by remember { mutableStateOf("") }
    val sortState by viewModel.getSortState.collectAsState(initial = false)
    var showArchiveSheetState by remember { mutableStateOf(false) }
    var showSearchField by remember { mutableStateOf(false) }

    if (showArchiveSheetState) {
        ModalBottomSheet(
            onDismissRequest = { showArchiveSheetState = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ) {
                ArchiveSheet(onDismiss = { showArchiveSheetState = false })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )
        {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 15.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                text = "QuickNotes"
            )


            IconButton(
                modifier = Modifier.padding(0.dp),
                onClick = {
                    if (showSearchField) {
                        showSearchField = false
                        searchTextInput = ""

                    } else {
                        showSearchField = true
                    }
                }
            ) {
                Image(
                    modifier = Modifier.scale(1.6f),
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Archive",
                    colorFilter = ColorFilter.tint(ArchivIcon),
                )
            }


            IconButton(
                modifier = Modifier.padding(0.dp),
                onClick = {
                    showArchiveSheetState = true
                }
            ) {
                Image(
                    modifier = Modifier.scale(1.6f),
                    painter = painterResource(id = R.drawable.baseline_archive_24),
                    contentDescription = "Archive",
                    colorFilter = ColorFilter.tint(ArchivIcon),
                )
            }
        }

        HorizontalDivider(
            thickness = 3.dp,
            color = White.copy(alpha = 0.5f),
            modifier = Modifier.padding(0.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )
        {

            if (showSearchField) {
                OutlinedTextField(
                    value = searchTextInput,
                    onValueChange = { searchTextInput = it },
                    modifier = Modifier
                        .width(180.dp)
                        .height(55.dp)
                        .align(Alignment.Top),
                    shape = ButtonDefaults.elevatedShape,
                    singleLine = false,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = White,
                        unfocusedBorderColor = White,
                        focusedContainerColor = White.copy(alpha = 0.5f),
                        unfocusedContainerColor = White.copy(alpha = 0.5f),
                    ),
                    placeholder = { Text("Suche") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                text = if (sortState) "Neue zuerst" else "Filter"
            )

            Switch(modifier = Modifier
                .padding(horizontal = 0.dp)
                .scale(0.6f),
                checked = sortState,
                onCheckedChange = {
                    viewModel.saveSortState(it)
                }
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_sort_24),
                contentDescription = "Archive",
                colorFilter = ColorFilter.tint(White)
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = White.copy(alpha = 0.5f),
            modifier = Modifier.padding(0.dp)
        )

        ItemList(modifier = modifier.weight(1f))

        HorizontalDivider(
            thickness = 3.dp,
            color = White.copy(alpha = 0.5f),
            modifier = Modifier.padding(0.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.weight(1f),
                shape = ButtonDefaults.elevatedShape,
                singleLine = false,
                colors = outlinedTextFieldColors(
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    focusedContainerColor = White.copy(alpha = 0.5f),
                    unfocusedContainerColor = White.copy(alpha = 0.5f),
                ),
                placeholder = { Text("QuickNote hinzufügen") }
            )
            ElevatedButton(
                onClick = {
                    var localDateTimeNow =
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

                    val filteredTitle = textInput.split(" ")
                        .filter { it.length > 4 }
                        .joinToString(" ")
                        .take(35)

                    viewModel.insert(
                        Todo(
                            todoTitle = filteredTitle.uppercase(),
                            todoText = textInput,
                            date = localDateTimeNow,
                            isDone = false,
                            isArchived = false,
                        )
                    )
                    textInput = ""
                },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(Blue),
                border = BorderStroke(1.dp, White),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text("Speichern", color = White)
            }
        }
    }
}

