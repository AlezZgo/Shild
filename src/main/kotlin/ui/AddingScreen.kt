package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import db.CommonTable
import db.toRussian
import org.jetbrains.exposed.sql.Table
import org.jetbrains.skia.impl.Log
import ui.mainScreen.AppViewModel

@Composable
fun AddingScreen(
    viewModel: AppViewModel,
    table : CommonTable
) {
    var added by remember { mutableStateOf(false) }
    var fields by remember { mutableStateOf((table as Table).columns.drop(1)) }
    val newModelParams by remember { mutableStateOf(fields.map { "" }.toMutableList()) }

    Column {

        if (added) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Объект добавлен в базу данных!", modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Button(modifier = Modifier.padding(4.dp).fillMaxWidth(), onClick = {
                if(newModelParams.all { it.isNotEmpty() }){
                    viewModel.create(newModelParams.toList(),table)
                    added = true
                }
            }) {
                Text(text = "Создать")
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                itemsIndexed(fields) { index, column ->
                    Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {

                        var content by remember { mutableStateOf(newModelParams[index]) }

                        Text(
                            text = column.name.toRussian(),
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = content,
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            onValueChange = { newValue ->
                                if (newValue.length > 99) return@OutlinedTextField

                                content = newValue
                                newModelParams[index] = newValue

                            })
                    }
                }
            }


        }
    }

}