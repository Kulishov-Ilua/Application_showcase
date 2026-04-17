package ru.kulishov.application_showcase.data.remote.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.Add
import ru.kulishov.application_showcase.domain.model.Story
import ru.kulishov.application_showcase.domain.repository.AddRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddRepositoryImpl @Inject constructor(): AddRepository
{
    override suspend fun getAdd(): List<Add> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getLastAdd")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<List<Add>>()
            return body
        }else return emptyList()
    }
}