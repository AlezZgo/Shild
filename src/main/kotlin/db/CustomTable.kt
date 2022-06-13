package db

import ui.filters.Filter

interface CustomTable {

    fun filters() : List<Filter>
}