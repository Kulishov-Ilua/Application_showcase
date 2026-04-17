package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Logo(
    val id: Int,
    val appId: Int,
    val logo: ByteArray
)
