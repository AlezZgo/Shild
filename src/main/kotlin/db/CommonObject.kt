package db

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

interface CommonObject : Update {
    fun previewText() : String

    @Composable
    fun UIList(isEditMode:Boolean)

//    fun listOfParams() : List<String>
}
