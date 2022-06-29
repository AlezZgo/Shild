package db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ui.filters.Filter

object Addresses : IntIdTable(), CommonTable {
    val type = varchar("type", 100)
    val ate = varchar("ate", 100)
    val locality = varchar("locality", 100)
    val street = varchar("street", 100)
    val house = varchar("house", 100)
    val building = varchar("building", 100)
    val apartment = varchar("apartment", 100)

    override fun filters(): List<Filter> = columns.drop(1).map {
        Filter(it)
    }

    override suspend fun all(filters: List<Filter>): List<CommonObject> {
        return transaction {
            val selected = selectAll()

            filters.forEachIndexed{index, filter->
                selected.andWhere {
                    (filter.column as Column<String>).upperCase().like("%${filter.value.uppercase()}%")
                }
            }

            return@transaction Address.wrapRows(selected).toList()
        }
    }

    override suspend fun addNew(list: List<String>) {
        transaction {
            Address.new {
                type = list[0]
                ate = list[1]
                locality = list[2]
                street = list[3]
                house = list[4]
                building = list[5]
                apartment = list[6]
            }

        }
    }

}

class Address(id: EntityID<Int>) : IntEntity(id),CommonObject {
    companion object : IntEntityClass<Address>(Addresses)

    var type by Addresses.type
    var ate by Addresses.ate
    var locality  by Addresses.locality
    var street by Addresses.street
    var house by Addresses.house
    var building  by Addresses.building
    var apartment by Addresses.apartment

    var persons by Person via PersonsAddresses

    override fun previewText() = "$street $house $building $apartment"

    override fun listOfValues() = listOf(type,
            ate,
            locality,
            street,
            house,
            building,
            apartment)

    override suspend fun listOfLinks(): List<Pair<CommonTable,List<CommonObject>>> {
        return transaction {
            listOf(Persons to persons.toList())
        }
    }

    override fun table(): Table = Addresses

    override suspend fun edit(newValues: List<String>) {
        transaction {
            type = newValues[0]
            ate = newValues[1]
            locality = newValues[2]
            street = newValues[3]
            house = newValues[4]
            building = newValues[5]
        }
    }

    override suspend fun remove() {


        transaction {
            delete()
        }
    }
}


