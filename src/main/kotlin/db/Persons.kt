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

object Persons : IntIdTable(), CommonTable {
    val name = varchar("name", 100).uniqueIndex()
    val obj = varchar("object", 100)
    val jobPosition = varchar("jobPosition", 100)
    val militaryRank = varchar("militaryRank", 100)
    val sex = varchar("sex", 100)
    val maidenName = varchar("maidenName", 100)
    val birthDay = varchar("birthDay", 100)
    val birthPlace = varchar("birthPlace", 100)
    val birthCountry = varchar("birthCountry", 100)
    val nationality = varchar("nationality", 100)
    val citizen = varchar("citizen", 100)
    val admissionForm = varchar("admissionForm", 100)

    override fun filters(): List<Filter> = columns.drop(1).map {
        Filter(it)
    }

    override suspend fun all(filters: List<Filter>): List<CommonObject> {
        return transaction {

            val selected = selectAll()

            filters.forEachIndexed { index, filter ->
                selected.andWhere {
                    (filter.column as Column<String>).upperCase().like("%${filter.value.uppercase()}%")
                }
            }

            return@transaction Person.wrapRows(selected).toList()
        }
    }

}

class Person(id: EntityID<Int>) : IntEntity(id), CommonObject {
    companion object : IntEntityClass<Person>(Persons)

    var name by Persons.name
    var obj by Persons.obj
    var jobPosition by Persons.jobPosition
    var militaryRank by Persons.militaryRank
    var sex by Persons.sex
    var maidenName by Persons.maidenName
    var birthDay by Persons.birthDay
    var birthPlace by Persons.birthPlace
    var birthCountry by Persons.birthCountry
    var nationality by Persons.nationality
    var citizen by Persons.citizen
    var admissionForm by Persons.admissionForm

    var addresses by Address via PersonsAddresses

    override fun previewText() = name

    @Composable
    override fun UIList(isEditMode: Boolean) {

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ListItem(Persons.name.name.toRussian(), name,isEditMode)
            ListItem(Persons.obj.name.toRussian(), obj,isEditMode)
            ListItem(Persons.jobPosition.name.toRussian(), jobPosition,isEditMode)
            ListItem(Persons.militaryRank.name.toRussian(), militaryRank,isEditMode)
            ListItem(Persons.sex.name.toRussian(), sex,isEditMode)
            ListItem(Persons.maidenName.name.toRussian(), maidenName,isEditMode)
            ListItem(Persons.birthDay.name.toRussian(), birthDay,isEditMode)
            ListItem(Persons.birthPlace.name.toRussian(), birthPlace,isEditMode)
            ListItem(Persons.birthCountry.name.toRussian(), birthCountry,isEditMode)
            ListItem(Persons.nationality.name.toRussian(), nationality,isEditMode)
            ListItem(Persons.citizen.name.toRussian(), citizen,isEditMode)
            ListItem(Persons.admissionForm.name.toRussian(), admissionForm,isEditMode)
        }
    }

//    override fun listOfParams(): List<String> {
//        TODO("Not yet implemented")
//    }

    override suspend fun update(updatedParams: List<String>) {
        transaction {
            name = updatedParams[0]
            obj = updatedParams[1]
            jobPosition = updatedParams[2]
            militaryRank = updatedParams[3]
            sex = updatedParams[4]
            maidenName = updatedParams[5]
            birthDay = updatedParams[6]
            birthPlace = updatedParams[7]
            birthCountry = updatedParams[8]
            nationality = updatedParams[9]
            citizen = updatedParams[10]
            admissionForm = updatedParams[11]
        }
    }
}
