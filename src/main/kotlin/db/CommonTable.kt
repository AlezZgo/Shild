package db

import ui.filters.Filter

interface CommonTable  {

    fun filters() : List<Filter>

    suspend fun all(filters : List<Filter>) : List<CommonObject>

    suspend fun addNew(list : List<String>)

}