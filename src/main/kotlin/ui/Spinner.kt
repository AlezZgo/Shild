package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import db.toRussian
import ui.mainScreen.AppViewModel

@Composable
fun TablesSpinner(viewModel: AppViewModel) {

    var expanded by remember { mutableStateOf(false) }
    val currentTable by remember { mutableStateOf(viewModel.currentTable) }

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
        Row(
            Modifier
                .padding(18.dp)
                .clickable {
                    expanded = !expanded
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) { // Anchor view
            Text(
                text = viewModel.currentTable.value.tableName.toRussian(),
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")

            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                viewModel.tables.forEach { table ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        currentTable.value = table
//                        viewModel.filters.value.clear()
//                        viewModel.refresh()
                    }) {
                        Text(text = table.tableName.toRussian())
                    }
                }
            }
        }
    }

}