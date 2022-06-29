package ui.popUpMenu

import db.CommonObject
import db.CommonTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopUpMenuViewModel(table : CommonTable) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _linkCommons = MutableStateFlow(emptyList<CommonObject>())
    val linkCommons get() = _linkCommons.asStateFlow()

    init {
        coroutineScope.launch {
            _linkCommons.emit(
                table.all(emptyList())
            )
        }
    }
}