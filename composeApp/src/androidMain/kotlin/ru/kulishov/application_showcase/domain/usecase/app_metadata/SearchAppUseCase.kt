package ru.kulishov.application_showcase.domain.usecase.app_metadata

import ru.kulishov.application_showcase.domain.model.AppMetadata
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.repository.AppMetadataRepository
import javax.inject.Inject

class SearchAppUseCase @Inject constructor(
    private val repository: AppMetadataRepository
) {
    suspend operator fun invoke(text: String): List<AppMetadataWithLogo> = repository.searchApp(text)
}