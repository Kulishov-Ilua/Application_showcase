package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.Category

interface CategoryRepository {
   suspend fun getPriorityCategory():List<Category>
    suspend fun getCategory(): List<Category>
}