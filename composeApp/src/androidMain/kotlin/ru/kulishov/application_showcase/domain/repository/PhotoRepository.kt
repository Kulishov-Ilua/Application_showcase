package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.Photo

interface PhotoRepository {
    suspend fun getPhoto(id: Long): Photo?
    suspend fun getAppPhotos(id:Int): List<Long>
}