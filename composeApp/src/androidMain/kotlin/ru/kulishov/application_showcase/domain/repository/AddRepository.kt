package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.Add

interface AddRepository {
    suspend fun getAdd(): List<Add>
}