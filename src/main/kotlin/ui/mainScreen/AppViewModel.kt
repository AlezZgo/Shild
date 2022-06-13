package ui.mainScreen

import db.CustomTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Table
import ui.filters.Filter

class AppViewModel(val tables: List<Table>) {

    var currentTable = MutableStateFlow(tables.first() as CustomTable)

    var filters = MutableStateFlow(mutableListOf<Filter>())

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

}
