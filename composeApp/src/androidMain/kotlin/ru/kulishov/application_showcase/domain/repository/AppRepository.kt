package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo

interface AppRepository {
    suspend fun getApp(id: Int): AppWithLogo?
}