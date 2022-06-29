package ui.popUpMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import db.CommonTable

@Composable
fun PopUpMenu(table: CommonTable) {
    var expanded by remember { mutableStateOf(false) }
    val popUpMenuViewModel = PopUpMenuViewModel(table)
    val linkCommons = popUpMenuViewModel.linkCommons.collectAsState()

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
        Row(
            Modifier
                .padding(18.dp)
                .clickable {
                    expanded = !expanded
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) { // Anchor view
            Text(
                text = "Добавить новый",
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp).align(Alignment.CenterVertically)
            )
            Icon(imageVector = Icons.Filled.Add, contentDescription = "")

            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {

                linkCommons.value.forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false

                    }) {
                        Text(text = it.previewText())
                    }
                }
            }

        }
    }
}





