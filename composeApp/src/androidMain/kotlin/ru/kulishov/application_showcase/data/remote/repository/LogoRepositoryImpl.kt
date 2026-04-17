package ru.kulishov.application_showcase.data.remote.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.Logo
import ru.kulishov.application_showcase.domain.repository.LogoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoRepositoryImpl @Inject constructor(): LogoRepository {
    override suspend fun getLogo(id: Int): Logo? {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getAppLogo/$id")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<Logo>()
            return body
        }else return null

    }
}