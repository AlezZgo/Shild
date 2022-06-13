package ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import db.CommonObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ObjectPreviewCard(
    model: CommonObject,
    onClick: () -> Unit
) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text = model.previewText(), modifier = Modifier.padding(16.dp))
    }
}