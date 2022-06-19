package db

import org.jetbrains.exposed.sql.Table

interface CommonObject : Edit {
    fun previewText() : String

    fun listOfValues() : List<String>

    suspend fun listOfLinks():  List<Pair<String,List<CommonObject>>>

    fun table() : Table



    override suspend fun edit(newValues: List<String>)



}
