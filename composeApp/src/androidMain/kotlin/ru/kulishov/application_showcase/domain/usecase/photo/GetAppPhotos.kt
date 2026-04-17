package ru.kulishov.application_showcase.domain.usecase.photo

import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.repository.PhotoRepository
import javax.inject.Inject

class GetAppPhotos @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id:Int): List<Long> = repository.getAppPhotos(id)
}