package ru.kulishov.application_showcase.domain.model

data class StoryWithPhoto(
    val data: Story,
    val photo: ByteArray? = null,
    val appMetadata: AppMetadataWithLogo?=null,

)
