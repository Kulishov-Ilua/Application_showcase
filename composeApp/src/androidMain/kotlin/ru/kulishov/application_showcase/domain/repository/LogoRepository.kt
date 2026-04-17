package ru.kulishov.application_showcase.domain.repository

import ru.kulishov.application_showcase.domain.model.Logo

interface LogoRepository {
    suspend fun getLogo(id:Int): Logo?
}