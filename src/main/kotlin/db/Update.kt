package db

interface Update {

    suspend fun update(updatedParams : List<String> )
}