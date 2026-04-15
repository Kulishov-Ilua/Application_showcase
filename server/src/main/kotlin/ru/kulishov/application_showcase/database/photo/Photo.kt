package ru.kulishov.application_showcase.database.photo

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table
import ru.kulishov.application_showcase.database.app.Apps

object Photos: Table(){
    val id = long("id").autoIncrement()
    val app_id = integer("app_id").references(Apps.id)

    val type = varchar("type", 100)
    val data = binary("data")
    val size = long("size")

    val is_view = bool("is_view")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Photo(
    val id: Long,
    val appId:Int,
    val type: String,
    val data: ByteArray,
    val size: Long,
    val isView: Boolean
)
