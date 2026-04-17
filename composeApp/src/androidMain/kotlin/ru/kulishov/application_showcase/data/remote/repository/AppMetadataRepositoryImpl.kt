package ru.kulishov.application_showcase.data.remote.repository

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.AppMetadata
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.repository.AppMetadataRepository
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetadataRepositoryImpl @Inject constructor(): AppMetadataRepository {
    override suspend fun getPopularApp(limit: Int): List<AppMetadataWithLogo> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getPopularApp/$limit")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<List<AppMetadata>>().map { AppMetadataWithLogo(
                id = it.id,
                name = it.name,
                category = it.category,
                short_description = it.short_description,
                grade = it.grade,
                logo = null
            ) }
            return body
        } else return emptyList()

    }

    override suspend fun getAppMetadata(id: Int): AppMetadataWithLogo? {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getAppMetadata/$id")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<AppMetadata>()
             val bodyWithLogo = AppMetadataWithLogo(
                id = body.id,
                name = body.name,
                category = body.category,
                short_description = body.short_description,
                grade = body.grade,
                logo = null
            )
            return bodyWithLogo
        } else return null
    }

    override suspend fun getCategoryApp(
        category: Int,
        limit: Int
    ): List<AppMetadataWithLogo> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getCategoryApp/$category/$limit")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<List<AppMetadata>>().map { AppMetadataWithLogo(
                id = it.id,
                name = it.name,
                category = it.category,
                short_description = it.short_description,
                grade = it.grade,
                logo = null
            ) }
            return body
        }else return emptyList()

    }

    override suspend fun searchApp(text: String): List<AppMetadataWithLogo> {
        val encodedText = URLEncoder.encode(text, "UTF-8")
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/searchApp/$encodedText")
        if(req.status== HttpStatusCode.OK) {
            val body = req.body<List<AppMetadata>>().map {
                AppMetadataWithLogo(
                    id = it.id,
                    name = it.name,
                    category = it.category,
                    short_description = it.short_description,
                    grade = it.grade,
                    logo = null
                )
            }
            return body
        }else return emptyList()
    }
}