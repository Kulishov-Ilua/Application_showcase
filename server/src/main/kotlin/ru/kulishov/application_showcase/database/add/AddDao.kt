package ru.kulishov.application_showcase.database.add

import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.kulishov.application_showcase.database.query


class AddDao {
    private fun rowAdd(row: ResultRow)= Add(
        id = row[Adds.id],
        appId = row[Adds.app_id],
        photoId = row[Adds.photo_id],
        createdAt = row[Adds.createdAt].toKotlinLocalDateTime()
    )

    suspend fun addAdd(add: Add) = query {
        Adds.insert {
            it[app_id] = add.appId
            it[photo_id]=add.photoId
            it[createdAt]= java.time.LocalDateTime.now()
        }
    }
    suspend fun deleteAdd(id: Long) = query {
        Adds.deleteWhere { Adds.id eq id }
    }

    suspend fun getLastAdds(): List<Add> = query {
        return@query Adds.selectAll().orderBy(Adds.createdAt to SortOrder.DESC).limit(10).map { rowAdd(it) }
    }
}