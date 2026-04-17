package ru.kulishov.application_showcase.domain.usecase.photo

import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.repository.AppMetadataRepository
import ru.kulishov.application_showcase.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id:Long): Photo? = repository.getPhoto(id)
}