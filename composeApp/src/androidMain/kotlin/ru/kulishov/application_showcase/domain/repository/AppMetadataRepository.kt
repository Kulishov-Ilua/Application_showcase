package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.AppMetadata
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo

interface AppMetadataRepository {
    suspend fun getPopularApp(limit:Int): List<AppMetadataWithLogo>
    suspend fun getAppMetadata(id:Int): AppMetadataWithLogo?
    suspend fun getCategoryApp(category:Int,limit:Int): List<AppMetadataWithLogo>
    suspend fun searchApp(text: String): List<AppMetadataWithLogo>
}