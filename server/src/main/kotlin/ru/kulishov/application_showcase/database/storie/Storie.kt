package ru.kulishov.application_showcase.database.storie

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime
import ru.kulishov.application_showcase.database.app.Apps
import ru.kulishov.application_showcase.database.photo.Photos

object Stories: Table(){
    val id = long("id").autoIncrement()
    val app_id = integer("app_id").references(Apps.id)
    val photo_id = long("photo_id").references(Photos.id)
    val createdAt = datetime("createdAt")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Story(
    val id:Long,
    val appId: Int,
    val photoId:Long,
    val createdAt:LocalDateTime
)