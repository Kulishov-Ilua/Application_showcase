package ru.kulishov.application_showcase.domain.usecase.logo

import ru.kulishov.application_showcase.domain.model.Logo
import ru.kulishov.application_showcase.domain.repository.LogoRepository
import javax.inject.Inject

class GetLogoUseCase @Inject constructor(
    private val repository: LogoRepository
) {
    suspend operator fun invoke(id:Int): Logo? = repository.getLogo(id)
}