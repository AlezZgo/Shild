package db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PersonsAddresses : Table() {
    val person = reference("name", Persons, onDelete = ReferenceOption.CASCADE)
    val address = reference("address", Addresses, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(person, address)
}