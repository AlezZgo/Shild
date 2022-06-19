package db

interface Edit {

    suspend fun edit(newModel: CommonObject)
}