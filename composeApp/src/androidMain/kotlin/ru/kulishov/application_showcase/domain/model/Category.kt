package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id:Int,
    val name:String,
    val priority: Boolean,
    val icon: ByteArray
)