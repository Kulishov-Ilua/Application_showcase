package ru.kulishov.application_showcase.database.app

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table
import ru.kulishov.application_showcase.database.category.Categories

object Apps: Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 127)
    val author = varchar("author",127)
    val category = integer("category").references(Categories.id)
    val grade = float("grade")
    val grades_count = long("grades_count")
    val age = integer("age")
    val description = varchar("description", 1000)
    val short_description = varchar("short_description", 127)

    override val primaryKey = PrimaryKey(id)

}

object Logos: Table(){
    val id = integer("id").autoIncrement()
    val app_id = integer("app_id").references(Apps.id)
    val logo = binary("logo")
}

@Serializable
data class Logo(
    val id: Int,
    val appId: Int,
    val logo: ByteArray
)

@Serializable
data class App(
    val id:Int,
    val name:String,
    val author:String,
    val category:Int,
    val grade:Float,
    val gradesCount:Long,
    val age:Int,
    val description:String,
    val short_description:String
)

@Serializable
data class AppMetadata(
    val id:Int,
    val name:String,
    val category: Int,
    val short_description: String,
    val grade: Float
)