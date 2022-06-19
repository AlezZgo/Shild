package db

interface Edit {

    suspend fun edit(newModel: List<String>)
}