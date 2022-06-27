package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import db.toRussian
import org.jetbrains.exposed.sql.Table
import ui.mainScreen.AppViewModel

@Composable
fun AddingScreen(
    viewModel: AppViewModel,
) {
    var added by remember { mutableStateOf(false) }
    var fields by remember { mutableStateOf((viewModel.currentTable.value as Table).columns.drop(1)) }

    Column {
        if (added) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Объект добавлен в базу данных!", modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Button(modifier = Modifier.padding(4.dp).fillMaxWidth(), onClick = {
                //todo create
            }) {
                Text(text = "Создать")
            }
            LazyColumn(modifier = Modifier.padding(4.dp)) {

                var content by remember { mutableStateOf("") }

                items(fields) {
                    OutlinedTextField(value = content,
                        singleLine = true,
                        onValueChange = { newValue ->
                            if (newValue.length > 99) return@OutlinedTextField

                            viewModel.refresh()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(it.name.toRussian()) })
                }
            }


        }
    }

}