package ru.kulishov.application_showcase.database

import io.ktor.server.application.ApplicationEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.kulishov.application_showcase.database.add.Adds
import ru.kulishov.application_showcase.database.app.Apps
import ru.kulishov.application_showcase.database.category.Categories
import ru.kulishov.application_showcase.database.photo.Photos
import ru.kulishov.application_showcase.database.storie.Stories

object DBFactory {
    fun init(environment: ApplicationEnvironment){
        val url = environment.config.property("db.url").getString()
        val user = environment.config.property("db.user").getString()
        val password = environment.config.property("db.pass").getString()
        val pool = environment.config.property("db.pool").getString().toInt()

        Database.connect(url = url, driver = "org.postgresql.Driver", user = user, password = password)

        transaction {
            SchemaUtils.create(Categories)
            SchemaUtils.create(Apps)
            SchemaUtils.create(Stories)
            SchemaUtils.create(Adds)
            SchemaUtils.create(Photos)
        }
    }
}

suspend fun<T> query(block: ()->T): T = withContext(Dispatchers.IO){
    transaction { block() }
}