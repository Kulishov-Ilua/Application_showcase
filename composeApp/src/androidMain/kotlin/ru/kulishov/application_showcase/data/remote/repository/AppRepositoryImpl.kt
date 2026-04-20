package ru.kulishov.application_showcase.data.remote.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.App
import ru.kulishov.application_showcase.domain.model.AppMetadata
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo
import ru.kulishov.application_showcase.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(): AppRepository {
    override suspend fun getApp(id: Int): AppWithLogo? {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getApp/$id")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<App>()
            val bodyWithLogo = AppWithLogo(
                app = body,
                logo = null
            )
            return bodyWithLogo
        } else return null
    }
}