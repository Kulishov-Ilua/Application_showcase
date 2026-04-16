package ru.kulishov.application_showcase.database.photo

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import ru.kulishov.application_showcase.database.query

class PhotoDao {
    private fun rowPhoto(row: ResultRow) = Photo(
        id = row[Photos.id],
        appId = row[Photos.app_id],
        type = row[Photos.type],
        data = row[Photos.data],
        size = row[Photos.size],
        isView = row[Photos.is_view]
    )

    suspend fun addPhoto(photo: Photo) = query {
        Photos.insert {
            it[Photos.app_id]=photo.appId
            it[Photos.type]=photo.type
            it[Photos.data]=photo.data
            it[Photos.size]=photo.size
            it[Photos.is_view]=photo.isView
        }
    }

    suspend fun deletePhoto(id: Long) = query {
        Photos.deleteWhere { Photos.id eq id }
    }

    suspend fun updatePhoto(photo: Photo) = query {
        Photos.update({ Photos.id eq photo.id}) {
            it[Photos.app_id]=photo.appId
            it[Photos.type]=photo.type
            it[Photos.data]=photo.data
            it[Photos.size]=photo.size
            it[Photos.is_view]=photo.isView
        }
    }

    suspend fun getPhotoByApp(id:Int):List<Long> =query {
        return@query Photos.select(Photos.id).where{ (Photos.app_id eq id) and  (Photos.is_view eq true)}.map { it[Photos.id] }
    }

    suspend fun getPhotoById(id: Long): Photo? = query {
        return@query Photos.selectAll().where{ Photos.id eq id }.map { rowPhoto(it) }.singleOrNull()
    }
}