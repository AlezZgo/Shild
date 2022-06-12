package db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Addresses : IntIdTable() {
    val type = varchar("type", 100)
    val ate = varchar("ate", 100)
    val locality = varchar("locality", 100)
    val street = varchar("street", 100)
    val house = varchar("house", 100)
    val building = varchar("building", 100)
    val apartment = varchar("apartment", 100)
}

class Address(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Address>(Addresses)

    var type by Addresses.type
    var ate by Addresses.ate
    var locality  by Addresses.locality
    var street by Addresses.street
    var house by Addresses.house
    var building  by Addresses.building
    var apartment by Addresses.apartment

    var persons by Person via PersonsAddresses
}


