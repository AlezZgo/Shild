package db

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upperCase
import ui.ListItem
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

    @Composable
    override fun UIList(isEditMode: Boolean) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ListItem(Addresses.type.name.toRussian(), type,isEditMode)
            ListItem(Addresses.ate.name.toRussian(), ate,isEditMode)
            ListItem(Addresses.locality.name.toRussian(), locality,isEditMode)
            ListItem(Addresses.street.name.toRussian(), street,isEditMode)
            ListItem(Addresses.house.name.toRussian(), house,isEditMode)
            ListItem(Addresses.building .name.toRussian(), building,isEditMode)
            ListItem(Addresses.apartment.name.toRussian(), apartment,isEditMode)
        }
    }

//    override fun listOfParams(): List<String> {
//        TODO("Not yet implemented")
//    }

    override suspend fun update(updatedParams: List<String>) {
        transaction {
            type = updatedParams[0]
            ate = updatedParams[1]
            locality = updatedParams[2]
            street = updatedParams[3]
            house = updatedParams[4]
            building = updatedParams[5]
        }
    }
}


