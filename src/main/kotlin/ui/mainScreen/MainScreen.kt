package ui.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import db.toRussian
import extensions.screens.openWindow
import ui.TablesSpinner
import ui.views.ObjectPreviewCard

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: AppViewModel,
) {
    val commons = viewModel.commons.collectAsState()
    var saved by remember { mutableStateOf(false) }
    val currentTable = viewModel.currentTable.collectAsState()

    if (saved) {
        AlertDialog(onDismissRequest = {},
            title = { Text("Экспорт данных в Excel") },
            confirmButton = {
                Button(onClick = {
                    //todo save
                    saved = false
                }) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    saved = false
                }) {
                    Text("Отмена")
                }
            },
            text = { Text("Вы уверены?") })
    }

    Row(
        modifier = Modifier.background(Color.LightGray)
    ) {
        Card(
            modifier = Modifier.width(180.dp).fillMaxHeight().padding(4.dp)
        ) {

            Column {
                TablesSpinner(viewModel)
                Row {
                    Button(modifier = Modifier.weight(1f).padding(4.dp), onClick = {

                        //todo open window
                    }) {
                        Image(
                            painterResource("plus.png"),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Button(modifier = Modifier.weight(1f).padding(4.dp),
                        onClick = {
                            saved = true
                        }) {
                        Image(
                            painterResource("excel_office.png"),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }

            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column {
                Card(modifier = Modifier.padding(top = 4.dp, end = 4.dp, bottom = 4.dp)) {
                    Column {
                        LazyVerticalGrid(
                            cells = GridCells.Fixed(4)
                        ) {
                            items(currentTable.value.filters()) { filter ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    var field by remember { mutableStateOf("") }
                                    OutlinedTextField(value = field,
                                        singleLine = true,
                                        onValueChange = { newValue ->
                                            if (newValue.length > 100) return@OutlinedTextField
                                            field = newValue
                                            viewModel.filters.value.removeIf {
                                                it.column == filter.column
                                            }
                                            if (newValue.isNotEmpty()) {
                                                viewModel.filters.value.add(filter.copy(value = newValue))
                                            }
                                            viewModel.refresh()
                                        },
                                        label = { Text(filter.column.name.toRussian()) })
                                }
                            }
                        }

                    }
                }
                Box(
                    modifier = Modifier.weight(1f).padding(top = 4.dp).fillMaxSize()
                ) {
                    val state = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier, state
                    ) {
                        items(commons.value){ common->
                            ObjectPreviewCard(common) {
                                openWindow(common.previewText()) {

                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = state
                        )
                    )
                }
            }
        }
    }
}


