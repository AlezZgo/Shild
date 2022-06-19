package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import db.CommonObject
import db.toRussian
import ui.mainScreen.AppViewModel

@Composable
fun DescriptionScreen(
    model: CommonObject,
    viewModel: AppViewModel
) {

    var deleted by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var newValues by remember { mutableStateOf(model.listOfValues().toMutableList()) }

    Card(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        Column {
            if (deleted) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Объект удалён из базы данных", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                if (isEditMode) {
                    Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                        Button(modifier = Modifier.padding(4.dp).weight(1f), onClick = {
                            viewModel.edit(model,newValues.toList())

                            isEditMode = false
                        }) {
                            Text(text = "Сохранить")
                        }
                        OutlinedButton(modifier = Modifier.padding(4.dp).weight(1f),
                            border = BorderStroke(1.dp, Color.Blue),
                            onClick = {
                                isEditMode = false
                            }) {
                            Text(text = "Отмена")
                        }
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                        Button(modifier = Modifier.padding(4.dp).weight(1f), onClick = {
                            isEditMode = !isEditMode
                        }) {
                            Text(text = "Изменить")
                        }
                        Button(modifier = Modifier.padding(4.dp).weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Red,
                                contentColor = Color.White
                            ),
                            onClick = {
//                                viewModel.delete(UIModel(newModel.toList()), table)
                                deleted = true
                            }) {
                            Text(text = "Удалить", modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }
                }
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    model.table().columns.drop(1).forEachIndexed { index, column->

                        var content by remember { mutableStateOf(model.listOfValues().elementAt(index)) }

                        Text(
                            text = column.name.toRussian(),
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = content,
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            enabled = isEditMode,
                            onValueChange = { newValue ->
                                if (newValue.length > 100) return@OutlinedTextField

                                content = newValue
                                newValues[index] = newValue
                            })
                    }
                }

            }


        }
    }
}