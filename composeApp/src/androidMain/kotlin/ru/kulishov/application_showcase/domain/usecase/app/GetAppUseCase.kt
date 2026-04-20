package ru.kulishov.application_showcase.domain.usecase.app

import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo
import ru.kulishov.application_showcase.domain.repository.AppRepository
import javax.inject.Inject

class GetAppUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id:Int): AppWithLogo? = repository.getApp(id)
}