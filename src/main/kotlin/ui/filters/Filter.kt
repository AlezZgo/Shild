package ui.filters

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.StringColumnType

class Filter(val column: Column<*>, var value: String = "") : ChangeValue {
    override fun changeValue(newValue: String) {
        when (column.columnType) {
            is IntegerColumnType -> {
                if (!newValue.contains("[^0-9]")) {
                    value = newValue
                }
            }
            is StringColumnType -> {
                value = newValue
            }
            else -> throw RuntimeException("Filter know nothing about this type of column")

        }
    }

}

interface ChangeValue {
    fun changeValue(newValue: String)
}