package ru.kulishov.application_showcase.database.app

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.core.lowerCase
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import ru.kulishov.application_showcase.database.category.Categories
import ru.kulishov.application_showcase.database.query

class AppDao {
    private fun rowApp(row: ResultRow) = App(
        id = row[Apps.id],
        name =row[Apps.name],
        author = row[Apps.author],
        category = row[Apps.category],
        grade = row[Apps.grade],
        gradesCount = row[Apps.grades_count],
        age = row[Apps.age],
        description = row[Apps.description],
        short_description = row[Apps.short_description]
    )

    private fun rowLogo(row: ResultRow) = Logo(
        id = row[Logos.id],
        appId = row[Logos.app_id],
        logo = row[Logos.logo]
    )

    private fun rowAppMetadata(row: ResultRow) = AppMetadata(
        id = row[Apps.id],
        name =row[Apps.name],
        category = row[Apps.category],
        grade = row[Apps.grade],
        short_description = row[Apps.short_description]
    )

    suspend fun addApp(app:App) = query {
        Apps.insert {
            it[name]= app.name
            it[author] = app.author
            it[category] = app.category
            it[grade] = app.grade
            it[grades_count] = app.gradesCount
            it[age] = app.age
            it[description] = app.description
            it[short_description] = app.short_description
        }
    }

    suspend fun addAppIcon(data: Logo) = query {
        Logos.insert {
            it[app_id]=data.appId
            it[logo]=data.logo
        }
    }
    suspend fun updateAppIcon(data: Logo) = query {
        Logos.update({ Logos.id eq data.id}) {
            it[app_id]=data.appId
            it[logo]=data.logo
        }
    }
    suspend fun getLogo(id:Int): Logo? = query {
        return@query Logos.selectAll().where { Logos.app_id eq id }.map { rowLogo(it) }.singleOrNull()
    }

    suspend fun deleteApp(id:Int) = query {
        Apps.deleteWhere { Apps.id eq id}
    }

    suspend fun updateApp(app:App) = query {
        Apps.update ({ Apps.id eq app.id }){
            it[name]= app.name
            it[author] = app.author
            it[category] = app.category
            it[grade] = app.grade
            it[grades_count] = app.gradesCount
            it[age] = app.age
            it[description] = app.description
            it[short_description] = app.short_description
        }
    }

    suspend fun getAppByCategory(id:Int, limit:Int):List<AppMetadata> = query {
        return@query Apps.select(
            Apps.id,
            Apps.name,
            Apps.category,
            Apps.grade,
            Apps.short_description
        ).where{
            Apps.category eq id
        }.limit(limit).map { rowAppMetadata(it) }
    }

    suspend fun searchApp(text:String): List<AppMetadata> = query {
        var ret = mutableListOf<AppMetadata>()
        ret += Apps.select(
            Apps.id,
            Apps.name,
            Apps.category,
            Apps.grade,
            Apps.short_description
        ).where{
            Apps.name.lowerCase() like "%${text.lowercase()}%"
        }.limit(10).map { rowAppMetadata(it) }

        if(ret.size<10){
            ret+=Apps.select(
                Apps.id,
                Apps.name,
                Apps.category,
                Apps.grade,
                Apps.short_description
            ).orderBy(Apps.grade to SortOrder.DESC).limit(10-ret.size).map { rowAppMetadata(it) }
        }
        return@query ret
    }

    suspend fun getPopularApp( limit:Int):List<AppMetadata> = query {
        return@query Apps.select(
            Apps.id,
            Apps.name,
            Apps.category,
            Apps.grade,
            Apps.short_description
        ).orderBy(Apps.grade to SortOrder.DESC).limit(limit).map { rowAppMetadata(it) }
    }

    suspend fun getAppMetadata( id:Int):AppMetadata? = query {
        return@query Apps.select(
            Apps.id,
            Apps.name,
            Apps.category,
            Apps.grade,
            Apps.short_description
        ).where{ Apps.id eq id }.map { rowAppMetadata(it) }.singleOrNull()
    }

    suspend fun getApp(id:Int): App? = query {
        return@query Apps.selectAll().where{ Apps.id eq id }.map { rowApp(it) }.singleOrNull()
    }
}