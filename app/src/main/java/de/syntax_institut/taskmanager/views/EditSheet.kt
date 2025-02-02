package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.GrayAsh
import de.syntax_institut.taskmanager.ui.theme.GrayAshDark
import de.syntax_institut.taskmanager.ui.theme.PanelColor
import de.syntax_institut.taskmanager.ui.theme.White
import de.syntax_institut.taskmanager.viewModels.TodoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSheet(
    modifier: Modifier,
    selectedTodo: Todo?,
    viewModel: TodoViewModel = viewModel(),
    onDismiss: () -> Unit = {},
) {
    var newTitle by remember { mutableStateOf(selectedTodo?.todoTitle ?: "") }
    var newText by remember { mutableStateOf(selectedTodo?.todoText ?: "") }
    var newDate by remember { mutableStateOf(selectedTodo?.date ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GrayAshDark)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {

            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                shape = ButtonDefaults.elevatedShape,
                singleLine = false,
                label = { Text(text = "Titel", color = White) },
                colors = outlinedTextFieldColors(
                    focusedPlaceholderColor = PanelColor,
                    unfocusedPlaceholderColor = PanelColor,
                    focusedTextColor = PanelColor,
                    unfocusedTextColor = PanelColor,
                    focusedBorderColor = PanelColor,
                    unfocusedBorderColor = PanelColor,
                    focusedContainerColor = GrayAsh.copy(alpha = 0.5f),
                    unfocusedContainerColor = GrayAsh.copy(alpha = 0.5f),
                )
            )

            OutlinedTextField(
                value = newText,
                onValueChange = { newText = it },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                shape = ButtonDefaults.elevatedShape,
                singleLine = false,
                label = { Text(text = "Notiztext", color = White) },
                colors = outlinedTextFieldColors(
                    focusedPlaceholderColor = PanelColor,
                    unfocusedPlaceholderColor = PanelColor,
                    focusedTextColor = PanelColor,
                    unfocusedTextColor = PanelColor,
                    focusedBorderColor = PanelColor,
                    unfocusedBorderColor = PanelColor,
                    focusedContainerColor = GrayAsh.copy(alpha = 0.5f),
                    unfocusedContainerColor = GrayAsh.copy(alpha = 0.5f),
                )
            )
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(GrayAsh),
                border = BorderStroke(1.dp, PanelColor),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = {
                    var localDateTimeNow =
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

                    viewModel.update(
                        Todo(
                            id = selectedTodo?.id ?: 0,
                            todoTitle = newTitle,
                            todoText = newText,
                            date = localDateTimeNow,
                            isDone = selectedTodo?.isDone ?: false,
                        )
                    )
                    onDismiss()
                }) {
                Text("Speichern")
            }
        }
    }
}
