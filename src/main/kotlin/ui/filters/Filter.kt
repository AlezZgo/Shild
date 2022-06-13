package ui.filters

import org.jetbrains.exposed.sql.Column

data class Filter(val column: Column<*>, val value: String = "")


