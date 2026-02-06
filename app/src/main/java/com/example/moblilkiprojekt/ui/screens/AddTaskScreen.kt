package com.example.moblilkiprojekt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moblilkiprojekt.ui.viewmodel.TaskViewModel



@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val (title, setTitle) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }
    val (imageUri, setImageUri) = remember { mutableStateOf<String?>(null) }

    val picker = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        setImageUri(uri?.toString())
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {

    Text(text = "Dodaj zadanie")

        OutlinedTextField(
            value = title,
            onValueChange = setTitle,
            label = { Text("Tytuł") },
            modifier = Modifier.padding(top = 12.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = setDescription,
            label = { Text("Opis (opcjonalnie)") },
            modifier = Modifier.padding(top = 12.dp)
        )

        Button(
            onClick = {
                picker.launch("image/*")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (imageUri == null) "Dodaj zdjęcie" else "Zmień zdjęcie")
        }

        if (imageUri != null) {
            Text(
                text = "Wybrane zdjęcie: OK",
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                viewModel.addTask(title, description, imageUri = imageUri)
                navController.popBackStack() // wraca na listę
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Zapisz")
        }
    }
}
