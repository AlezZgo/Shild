package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(title: String, value: String, isEditMode: Boolean){

    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        fontWeight = FontWeight.Bold
    )

    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        enabled = isEditMode,
        onValueChange = { newValue ->

        })
}
