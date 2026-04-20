package ru.kulishov.application_showcase.data.remote.repository

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.kulishov.application_showcase.data.remote.KtorClient
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.repository.PhotoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(): PhotoRepository {
    override suspend fun getPhoto(id: Long): Photo? {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getPhotoById/$id")
        if(req.status== HttpStatusCode.OK){
            val body = req.body<Photo>()
            return body
        }else return null
    }

    override suspend fun getAppPhotos(id: Int): List<Long> {
        val req = KtorClient.client.get("${KtorClient.BASE_URL}/getPhotoListByApp/$id")
        if (req.status == HttpStatusCode.OK) {
            val body = req.body<List<Long>>()
            return body
        } else return emptyList()
    }
}