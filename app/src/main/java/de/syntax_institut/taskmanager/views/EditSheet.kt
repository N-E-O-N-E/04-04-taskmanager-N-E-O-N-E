package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.syntax_institut.taskmanager.data.Todo
import de.syntax_institut.taskmanager.ui.theme.GrayAshDark

@Composable
fun EditSheet(
    modifier: Modifier,
    selectedTodo: Todo?
) {
    var newTitle by remember { mutableStateOf("") }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = GrayAshDark)) {
        Column {

            TextField(
                value = selectedTodo?.todoTitle ?: "",
                onValueChange = { newTitle = it },
                modifier = modifier
            )

            Button(onClick = {

            }) {
                Text("Speichern")
            }
        }
    }
}
