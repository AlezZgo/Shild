package db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ui.filters.Filter
import kotlin.reflect.KClass

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

    override fun entity(): Class<Person> = Person::class.java

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

    override suspend fun remove() {

        transaction {
            delete()
        }
    }

    override fun listOfValues() = listOf(
        name,
        obj,
        jobPosition,
        militaryRank,
        sex,
        maidenName,
        birthDay,
        birthPlace,
        birthCountry,
        nationality,
        citizen,
        admissionForm
    )

    override suspend fun listOfLinks(): List<Pair<String,List<CommonObject>>> {
        return transaction {
            listOf(Addresses.tableName.toRussian() to addresses.toList())
        }
    }

    override fun table(): Table = Persons

    override suspend fun edit(newValues: List<String>) {

        transaction {
            name = newValues[0]
            obj = newValues[1]
            jobPosition = newValues[2]
            militaryRank = newValues[3]
            sex = newValues[4]
            maidenName = newValues[5]
            birthDay = newValues[6]
            birthPlace = newValues[7]
            birthCountry = newValues[8]
            nationality = newValues[9]
            citizen = newValues[10]
            admissionForm = newValues[11]
        }
    }


}
