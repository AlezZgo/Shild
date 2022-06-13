package ui.filters

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.FloatColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.StringColumnType

data class Filter(val column: Column<*>, var value: String = "")


