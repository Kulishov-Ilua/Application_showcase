package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: Long,
    val appId:Int,
    val type: String,
    val data: ByteArray,
    val size: Long,
    val isView: Boolean
)
