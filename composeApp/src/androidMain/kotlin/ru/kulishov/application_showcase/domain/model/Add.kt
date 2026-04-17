package ru.kulishov.application_showcase.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Add(
    val id:Long,
    val appId: Int,
    val photoId:Long,
    val createdAt:LocalDateTime
)