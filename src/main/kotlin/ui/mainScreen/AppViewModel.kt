package ui.mainScreen

import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.exposed.sql.Table

class AppViewModel(val tables: List<Table>) {

    var currentTable = MutableStateFlow(tables.first())

}
