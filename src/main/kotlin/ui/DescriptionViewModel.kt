package ui

import db.CommonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DescriptionViewModel(model: CommonObject) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _links = MutableStateFlow(emptyList<Pair<String,List<CommonObject>>>())
    val links get() = _links.asStateFlow()

    init {
        coroutineScope.launch {
            _links.emit(
                model.listOfLinks()
            )
        }
    }
}