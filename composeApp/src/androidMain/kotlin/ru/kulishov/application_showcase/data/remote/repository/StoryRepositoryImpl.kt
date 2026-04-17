package ru.kulishov.application_showcase.data.remote.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.Story
import ru.kulishov.application_showcase.domain.repository.StoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepositoryImpl @Inject constructor(): StoryRepository {
    override suspend fun getLastStories(): List<Story> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getLastStory")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<List<Story>>()
            return body
        }else return emptyList()
    }
}