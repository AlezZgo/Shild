package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    //todo viewModel: MainViewModel,
) {
    var saved by remember { mutableStateOf(false) }

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
                //todo Spinner
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
                            // todo filter items
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
                        //todo preview cards
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


