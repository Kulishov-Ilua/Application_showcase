package ru.kulishov.application_showcase.data.remote.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(): CategoryRepository {
    override suspend fun getPriorityCategory(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(): List<Category> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getCategory") {
            contentType(ContentType.Application.Json)
        }

        val body = req.body<List<Category>>()
        return body
    }
}