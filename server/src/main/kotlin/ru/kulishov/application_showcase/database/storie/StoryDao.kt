package ru.kulishov.application_showcase.database.storie

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.kulishov.application_showcase.database.query

class StoryDao {
    private fun rowStory(row: ResultRow) = Story(
        id = row[Stories.id],
        appId = row[Stories.app_id],
        photoId = row[Stories.photo_id],
        createdAt = row[Stories.createdAt].toKotlinLocalDateTime()
    )

    suspend fun addStory(story: Story) = query {
        Stories.insert {
            it[app_id] = story.appId
            it[photo_id]=story.photoId
            it[createdAt]= java.time.LocalDateTime.now()
        }
    }
    suspend fun deleteStory(id: Long) = query {
        Stories.deleteWhere { Stories.id eq id }
    }

    suspend fun getLastStories(): List<Story> = query {
        return@query Stories.selectAll().orderBy(Stories.createdAt to SortOrder.DESC).limit(10).map { rowStory(it) }
    }
}