package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import db.CommonObject
import org.jetbrains.exposed.sql.FloatColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.StringColumnType
import org.jetbrains.exposed.sql.Table

@Composable
fun ListItem(title: String,value: String){

    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        fontWeight = FontWeight.Bold
    )
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        enabled = false,
        onValueChange = { newValue ->

        })
}
