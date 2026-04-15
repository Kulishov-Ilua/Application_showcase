package ru.kulishov.application_showcase.database.category

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import ru.kulishov.application_showcase.database.query

class CategoryDao {
    private fun rowCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name],
        priority = row[Categories.priority],
        icon = row[Categories.icon]
    )

    suspend fun addCategory(category: Category) = query{
        Categories.insert {
            it[name] = category.name
            it[priority]=category.priority
            it[icon]=category.icon
        }
    }

    suspend fun deleteCategory(id:Int) = query {
        Categories.deleteWhere { Categories.id eq id }
    }

    suspend fun updateCategory(category: Category) = query {
        Categories.update( { Categories.id eq category.id }){
            it[name] = category.name
            it[priority]=category.priority
            it[icon]=category.icon
        }
    }

    suspend fun getPriorityCategory(): List<Category> = query {
        return@query Categories.selectAll().where{ Categories.priority eq true }
            .map { rowCategory(it) }
    }

    suspend fun getCategoryList(): List<Category> = query {
        return@query Categories.selectAll()
            .map { rowCategory(it) }
    }
}