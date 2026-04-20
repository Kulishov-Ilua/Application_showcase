package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppWithLogo(
    var app: App,
    var logo: ByteArray?=null,
    var category:Category? = null,
)
