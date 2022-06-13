package db

import androidx.compose.runtime.Composable

interface CommonObject {
    fun previewText() : String

    @Composable
    fun UIList()
}
