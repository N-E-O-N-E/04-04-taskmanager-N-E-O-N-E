package de.syntax_institut.taskmanager.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.taskmanager.data.todoSamples
import de.syntax_institut.taskmanager.ui.theme.PurpleGrey80

@Composable
fun TodoListItem(
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(horizontal = 20.dp)
    ) {
        items(todoSamples) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 3.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(PurpleGrey80),
                elevation = cardElevation(),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(it.title)
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, name = "TodoListItemPreview")
@Composable
private fun TodoListItemPreview() {
    TodoListItem()
}