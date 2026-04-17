package ru.kulishov.application_showcase.domain.usecase.category

import ru.kulishov.application_showcase.domain.model.AppMetadata
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> = repository.getCategory()
}