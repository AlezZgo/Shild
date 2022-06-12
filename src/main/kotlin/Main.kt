// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import db.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ui.mainScreen.AppViewModel
import ui.mainScreen.MainScreen
import java.sql.Connection

@Composable
@Preview
fun App() {

    val tablesUI = listOf(
        Persons,
        Addresses,
    )

    initDB()

    MaterialTheme {
        MainScreen(AppViewModel(tablesUI))
    }
}

private fun initDB(){

    val toFill = false

    val tables = listOf(
        Addresses,
        Persons,
        PersonsAddresses
    )

    val db = Database.connect("jdbc:h2:./db/db", "org.h2.Driver")

    TransactionManager.manager.defaultIsolationLevel =
        Connection.TRANSACTION_SERIALIZABLE

    TransactionManager.defaultDatabase = db

    transaction {
        tables.forEach {
            SchemaUtils.create(it)
        }
        if (toFill) {
            val addr1 = Address.new {
                type = "Зарегистрирован"
                ate = "Ленинградская область"
                locality = "пос. Гатчина"
                street = "Свободы"
                house = "1"
                building = "21"
                apartment = "121"
            }

            val addr2 = Address.new {
                type = "Зарегистрирован"
                ate = "Московская область"
                locality = "Щелково"
                street = "Березная"
                house = "8"
                building = "91"
                apartment = "151"
            }

            val addr3 = Address.new {
                type = "Проживает"
                ate = "Белгородская область"
                locality = "Новая наумовка"
                street = "Белых"
                house = "4"
                building = "11"
                apartment = ""
            }

            val sasha = Person.new {
                name = "Александр Григорьевич Мазякин"
                obj = "ВКА"
                jobPosition = "Начальник службы МТО"
                militaryRank = "Подполковник"
                sex = "Муж"
                maidenName = ""
                birthDay = "12.04.1986"
                birthPlace = "село Обжорная Закарпатская область"
                birthCountry = "Украина"
                nationality = "украинец"
                citizen = "Россия"
                admissionForm = 3
            }

            val dima = Person.new {
                name = "Дмитрий Владимирович Петров"
                obj = "ВКА"
                jobPosition = "Начальник продовольственной службы"
                militaryRank = "Майор"
                sex = "Муж"
                maidenName = ""
                birthDay = "17.01.1991"
                birthPlace = "г.Белгород Белгородская область"
                birthCountry = "Россия"
                nationality = "русский"
                citizen = "Россия"
                admissionForm = 2
            }

            val ann = Person.new {
                name = "Анна Петровна Мазякина"
                obj = "ВКА"
                jobPosition = "Повар"
                militaryRank = "Ефрейтор"
                sex = "Жен"
                maidenName = "Карепова"
                birthDay = "17.02.1990"
                birthPlace = "ПГТ Дороево Саратовская область"
                birthCountry = "Россия"
                nationality = "русская"
                citizen = "Россия"
                admissionForm = 4
            }

            sasha.addresses = SizedCollection(listOf(addr1))
            dima.addresses = SizedCollection(listOf(addr2))
            ann.addresses = SizedCollection(listOf(addr1, addr3))
        }
    }

}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        icon = painterResource("shield.png"),
        title = "Щит"
    ) {
        App()
    }
}
