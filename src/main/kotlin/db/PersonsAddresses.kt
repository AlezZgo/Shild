package db

import org.jetbrains.exposed.sql.Table

object PersonsAddresses : Table() {
    val person = reference("name", Persons)
    val address = reference("address", Addresses)
}