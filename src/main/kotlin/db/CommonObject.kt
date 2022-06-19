package db

import androidx.compose.runtime.Composable
import org.jetbrains.exposed.sql.Table

interface CommonObject : Edit {
    fun previewText() : String

    fun listOfValues() : List<String>

    fun table() : Table

    override suspend fun edit(newModel: CommonObject)



}
