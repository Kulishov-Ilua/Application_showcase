package ru.kulishov.application_showcase.domain.usecase.add

import ru.kulishov.application_showcase.domain.model.Add
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.repository.AddRepository
import javax.inject.Inject

class GetAddUseCase @Inject constructor(
    private val repository: AddRepository
) {
    suspend operator fun invoke(): List<Add> = repository.getAdd()
}