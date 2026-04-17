package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppMetadata(
    val id:Int,
    val name:String,
    val category: Int,
    val short_description: String,
    val grade: Float
)