package ui.mainScreen

import db.CommonObject
import db.CommonTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Table
import ui.filters.Filter

class AppViewModel(val tables: List<Table>) {

    var currentTable = MutableStateFlow(tables.first() as CommonTable)

    var filters = MutableStateFlow(mutableListOf<Filter>())

    private val _commons = MutableStateFlow(emptyList<CommonObject>())
    val commons get() = _commons.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        refresh()
    }

    fun refresh() {
        coroutineScope.launch {
            _commons.emit(
                currentTable.value.all(filters.value)
            )
        }
    }

    fun edit(model: CommonObject, newModel: CommonObject) {
        coroutineScope.launch {
            model.edit(newModel)
        }
    }


}
