package com.example.moblilkiprojekt.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moblilkiprojekt.data.Task
import com.example.moblilkiprojekt.ui.viewmodel.TaskViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: Long,
    viewModel: TaskViewModel = viewModel()
) {
    var task by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        task = viewModel.getTaskById(taskId)
    }

    val t = task

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Szczegóły zadania",
            style = MaterialTheme.typography.headlineSmall
        )

        if (t == null) {
            Text(text = "Ładowanie…", style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Wróć") }
            return@Column
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Tytuł", style = MaterialTheme.typography.labelLarge)
                Text(text = t.title, style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(4.dp))

                Text(text = "Opis", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = t.description.ifEmpty { "(brak)" },
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(4.dp))

                Text(text = "Zdjęcie", style = MaterialTheme.typography.labelLarge)

                if (t.imageUri != null) {
                    AsyncImage(
                        model = t.imageUri,
                        contentDescription = "Zdjęcie zadania",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)          // proporcje: 3:2 (możesz zmienić)
                            .clip(RoundedCornerShape(12.dp))
                    )
                } else {
                    Text(
                        text = "Brak",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }

        Button(
            onClick = {
                viewModel.deleteTask(t)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Usuń zadanie")
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Wróć")
        }
    }
}
