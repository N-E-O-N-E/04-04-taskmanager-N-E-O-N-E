package de.syntax_institut.taskmanager.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.viewModels.TodoViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoList(
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
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "LittleToDo"
            )

            Text(
                modifier = Modifier,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
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
                    var localDateTimeNow = LocalDate.now().toString()

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
                    textInput = "" //Leeren
                },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFDB999))
            ) {
                Text("SAVE", color = Color.Black)
            }
        }
    }
}

